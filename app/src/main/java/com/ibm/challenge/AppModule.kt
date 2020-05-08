package com.ibm.challenge

import com.ibm.challenge.core.Navigator
import com.ibm.challenge.core.mvp.BaseActivity
import com.ibm.challenge.data.repository.local.LocalRepositoryImpl
import com.ibm.challenge.domain.interactos.cache.GetCacheObject
import com.ibm.challenge.domain.interactos.cache.PutCacheObject
import com.ibm.challenge.domain.repository.LocalRepository
import com.ibm.challenge.presentation.view.splashscreen.SplashScreenPresenter
import io.paperdb.Paper
import org.koin.dsl.module

object AppModule {

    fun get() = module {
        // Data
        single<LocalRepository> {
            LocalRepositoryImpl(Paper.book())
        }

        // Domain
        single { GetCacheObject(get()) }
        single { PutCacheObject(get()) }

        // Presentation
        single { (activity: BaseActivity) ->  SplashScreenPresenter(Navigator(activity)) }
    }

}