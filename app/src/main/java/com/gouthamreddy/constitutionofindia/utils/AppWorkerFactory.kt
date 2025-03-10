package com.gouthamreddy.constitutionofindia.utils

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.gouthamreddy.constitutionofindia.data.models.ConstitutionDatabase
import com.gouthamreddy.constitutionofindia.domain.usecase.PDFDownloaderUseCase
import javax.inject.Inject

class AppWorkerFactory @Inject constructor(
    private val pdfParser: PdfParser,
    private val database: ConstitutionDatabase,
    private val constitutionParser: ConstitutionParser,
    private val pdfDownloaderUseCase: PDFDownloaderUseCase
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            PdfParseWorker::class.java.name -> {
                PdfParseWorker(
                    applicationContext = appContext,
                    workerParams = workerParameters,
                    pdfParser = pdfParser,
                    database = database,
                    constitutionParser = constitutionParser,
                    pdfDownloaderUseCase = pdfDownloaderUseCase
                )
            }
            PdfDownloadWorker::class.java.name -> {
                PdfDownloadWorker(
                    applicationContext = appContext,
                    workerParams = workerParameters,
                    pdfDownloaderUseCase = pdfDownloaderUseCase
                )
            }
            else -> null
        }
    }
}