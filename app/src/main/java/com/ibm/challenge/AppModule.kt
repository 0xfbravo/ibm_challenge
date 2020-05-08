package com.ibm.challenge

import com.google.gson.GsonBuilder
import com.ibm.challenge.core.JodaAdapter
import com.ibm.challenge.core.Navigator
import com.ibm.challenge.core.mvp.BaseActivity
import com.ibm.challenge.data.repository.local.LocalRepositoryImpl
import com.ibm.challenge.data.repository.security.CipherWrapper
import com.ibm.challenge.data.repository.security.KeystoreRepositoryImpl
import com.ibm.challenge.domain.interactos.cache.GetCacheObject
import com.ibm.challenge.domain.interactos.cache.PutCacheObject
import com.ibm.challenge.domain.repository.local.LocalRepository
import com.ibm.challenge.domain.repository.security.KeystoreRepository
import com.ibm.challenge.presentation.view.splashscreen.SplashScreenPresenter
import io.paperdb.Paper
import org.joda.time.DateTime
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import kotlin.math.sin

object AppModule {

    fun get() = module {
        // Data
        single { Paper.book() }
        single { GsonBuilder().serializeNulls().registerTypeAdapter(DateTime::class.java, JodaAdapter()).create() }
        single { CipherWrapper() }
        single<KeystoreRepository> { KeystoreRepositoryImpl(androidContext(), get()) }
        single<LocalRepository> { LocalRepositoryImpl(get(), get(), get(), get()) }

        // Domain
        single { GetCacheObject(get()) }
        single { PutCacheObject(get()) }

        // Presentation
        single { (activity: BaseActivity) ->  SplashScreenPresenter(Navigator(activity)) }
    }

}