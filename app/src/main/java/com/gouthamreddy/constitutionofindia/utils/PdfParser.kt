package com.gouthamreddy.constitutionofindia.utils

import android.util.Log
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

/*
Not required for current approach
class PdfParser() {

    suspend fun parsePdfFromLocal(filePath: String): String = withContext(Dispatchers.IO) {
        try {
            val file = File(filePath)
            if (!file.exists()) {
                Log.e("PdfParser", "File does not exist: $filePath")
                return@withContext ""
            }
            val document: PDDocument = PDDocument.load(file)
            val stripper = PDFTextStripper()
            val text = stripper.getText(document)
            document.close()
            text
        } catch (e: Exception) {
            Log.e("PdfParser", "Error parsing PDF", e)
            ""
        }
    }
}*/
