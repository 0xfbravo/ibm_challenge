package com.ibm.challenge.core

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.ibm.challenge.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class IBMChallengeApp: Application() {

    companion object {
        lateinit var appContext: Context
        lateinit var sharedPreferences: SharedPreferences
        var deviceUUID: String = ""
    }

    override fun onCreate() {
        super.onCreate()

        /* Set Application Context */
        appContext = applicationContext
        sharedPreferences = appContext.getSharedPreferences("IBMChallengeApp", Context.MODE_PRIVATE)

        /* Setup Koin */
        startKoin {
            androidContext(appContext)
            loadKoinModules(AppModule.get())
        }

    }

}