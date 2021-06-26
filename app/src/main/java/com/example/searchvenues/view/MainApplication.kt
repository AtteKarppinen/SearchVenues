package com.example.searchvenues.view

import android.app.Application
import android.util.Log
import com.example.searchvenues.di.koinModules
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin

/**
 * Application class needed for one-time initializations.
 */
class MainApplication : Application() {

    companion object {
        private const val LOG_TAG = "MainApplication"
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(LOG_TAG, "onCreate")

        startKoin {
            androidContext(this@MainApplication)
            fragmentFactory()
            modules(koinModules)
        }

        Log.d(LOG_TAG, "Koin initialized")
    }
}