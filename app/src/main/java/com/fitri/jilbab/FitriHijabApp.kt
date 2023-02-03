package com.fitri.jilbab

import android.app.Application
import androidx.viewbinding.BuildConfig
import com.fitri.jilbab.data.local.SharedPref
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class FitriHijabApp : Application() {

    override fun onCreate() {
        super.onCreate()
        setupTimber()
        setupHawk()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setupHawk() {
        SharedPref.appInit(this)
    }

}