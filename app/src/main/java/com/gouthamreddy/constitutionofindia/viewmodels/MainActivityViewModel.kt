package com.gouthamreddy.constitutionofindia.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.gouthamreddy.constitutionofindia.domain.usecase.PDFDownloaderUseCase
import com.gouthamreddy.constitutionofindia.utils.PdfDownloadWorker
import com.gouthamreddy.constitutionofindia.utils.PdfParseWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    val pdfDownloaderUseCase: PDFDownloaderUseCase,
    val workManager: WorkManager,
) : ViewModel() {




    fun schedulePdfParsing() {
        // Define constraints (e.g., require network)
        val constraints = Constraints.Builder()
            .setRequiresStorageNotLow(true)
            .build()

        // Create the download work request
        val downloadRequest = OneTimeWorkRequestBuilder<PdfDownloadWorker>()
            .setConstraints(constraints)
            .build()

        // Create the parsing work request
        val parseRequest = OneTimeWorkRequestBuilder<PdfParseWorker>()
            .setConstraints(constraints)
            .build()

        // Create the work chain
        val workChain = workManager.beginWith(downloadRequest)
            .then(parseRequest)

        // Enqueue the work chain
        workChain.enqueue()

        val request = PeriodicWorkRequestBuilder<PdfDownloadWorker>(
            repeatInterval = 1, // Run every 1 day
            repeatIntervalTimeUnit = TimeUnit.DAYS
        ).build()

        Log.d("MainActivityViewModel", "Scheduling PDF parsing")

        workManager.enqueueUniquePeriodicWork(
            "pdf_parsing_work", // Unique name for the work
            ExistingPeriodicWorkPolicy.REPLACE, // If it's already scheduled, keep the old one
            request
        )
    }

}