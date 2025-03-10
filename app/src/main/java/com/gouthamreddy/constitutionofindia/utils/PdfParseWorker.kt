package com.gouthamreddy.constitutionofindia.utils

import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.gouthamreddy.constitutionofindia.data.models.ConstitutionDatabase
import com.gouthamreddy.constitutionofindia.domain.execute
import com.gouthamreddy.constitutionofindia.domain.usecase.PDFDownloaderUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltWorker
class PdfParseWorker @AssistedInject constructor(
     @Assisted private val applicationContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val pdfParser: PdfParser,
    private val database: ConstitutionDatabase,
    private val constitutionParser: ConstitutionParser,
    private val pdfDownloaderUseCase: PDFDownloaderUseCase
) : CoroutineWorker(applicationContext, workerParams) {

    private val articleDao = database.articleDao()
    private val scheduleDao = database.scheduleDao()
    private val amendmentDao = database.amendmentDao()

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        var result = Result.failure()
        try {
            Log.d("MainActivityViewModel", "Scheduling PDF parsing DO work")
            val localFilePath =
                "${applicationContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)}/constitution.pdf"

            pdfDownloaderUseCase(Unit).execute(onSuccess = {
                Log.d("PdfParseWorker", "download started")
                val downloadCompleted = writeResponseBodyToDisk(it, localFilePath)

                Log.d("PdfParseWorker", " download finished")
                if (downloadCompleted) {
                    Log.d("MainActivityViewModel", " download complete.  ")
                    val pdfText = pdfParser.parsePdfFromLocal(localFilePath)
                    val articles = constitutionParser.parseArticles(pdfText)
                    val schedules = constitutionParser.parseSchedules(pdfText)
                    val amendments = constitutionParser.parseAmendments(pdfText)
                    articleDao.insertArticles(articles)
                    scheduleDao.insertSchedules(schedules)
                    amendmentDao.insertAmendments(amendments)
                    result = Result.success()
                } else {
                    Log.d("MainActivityViewModel", " download failed.  ")
                    result = Result.failure()
                }
            }, onError = {
                Log.d("MainActivityViewModel", " download failed.  ")
                result = Result.failure()
            })
            result
        } catch (e: Exception) {
            Log.e("PdfParseWorker", "Error during PDF processing", e)
            return@withContext Result.failure()
        }
    }

    private fun writeResponseBodyToDisk(body: ResponseBody, path: String): Boolean {
        return try {
            val file = File(path)
            if (file.exists()){
                file.delete()
            }
            val inputStream = body.byteStream()
            val outputStream = FileOutputStream(file)

            inputStream.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }
            true
        } catch (e: Exception) {
            Log.e("PdfParseWorker", "Error writing PDF to disk", e)
            false
        }
    }
}