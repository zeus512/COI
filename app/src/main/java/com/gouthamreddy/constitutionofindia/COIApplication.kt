package com.gouthamreddy.constitutionofindia

import android.app.Application
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class COIApplication: Application(){
    override fun onCreate() {
        super.onCreate()
        // PDFBoxResourceLoader.init(applicationContext);
    }


}