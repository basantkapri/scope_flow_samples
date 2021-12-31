package com.example.samples

import android.app.Application
import timber.log.Timber

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Debugging.TimberHyperLinkedDebugTree)
    }
}