package com.gouthamreddy.constitutionofindia.utils

import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
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
class PdfDownloadWorker @AssistedInject constructor(
    @Assisted  private val applicationContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val pdfDownloaderUseCase: PDFDownloaderUseCase
) : CoroutineWorker(applicationContext, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            Log.d("PdfDownloadWorker", "Starting download work")
            val localFilePath =
                "${applicationContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)}/constitution.pdf"
            val file = File(localFilePath)
            if (file.exists()) {
                Log.d("PdfDownloadWorker", "PDF already exists. Skipping download.")
                return@withContext Result.success()
            }
            val responseBodyResult = pdfDownloaderUseCase(Unit)


            return@withContext responseBodyResult.toWorkResult(
                onSuccess = { writeResponseBodyToDisk(it, localFilePath) },
                onFailureMessage = "PDF download failed",
                outputDataKey = "download_success",
            )
        } catch (e: Exception) {
            Log.e("PdfDownloadWorker", "Error during PDF download", e)
            Result.failure()
        }
    }
    private fun writeResponseBodyToDisk(body: ResponseBody, path: String): Boolean {
        return try {
            val file = File(path)
            if (file.exists()) {
                file.delete()
            }

            body.byteStream().use { inputStream ->
                FileOutputStream(file).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            true
        } catch (e: Exception) {
            Log.e("PdfDownloadWorker", "Error writing PDF to disk", e)
            false
        }
    }
}
