package com.gouthamreddy.constitutionofindia

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class COIApplication: Application(){
    override fun onCreate() {
        super.onCreate()
    }


}