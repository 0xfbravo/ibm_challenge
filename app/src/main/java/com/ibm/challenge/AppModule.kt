package com.ibm.challenge

import com.ibm.challenge.core.IBMChallengeApp
import com.ibm.challenge.core.Navigator
import com.ibm.challenge.core.mvp.BaseActivity
import com.ibm.challenge.data.repository.local.LocalRepositoryImpl
import com.ibm.challenge.domain.interactos.cache.GetCacheObject
import com.ibm.challenge.domain.interactos.cache.PutCacheObject
import com.ibm.challenge.domain.repository.LocalRepository
import com.ibm.challenge.presentation.view.splashscreen.SplashScreenPresenter
import io.realm.Realm
import io.realm.RealmConfiguration
import org.koin.dsl.module

object AppModule {

    fun get() = module {
        // Data
        single<LocalRepository> {
            val realmConfiguration = RealmConfiguration.Builder(IBMChallengeApp.appContext).build()
            val realm = Realm.getInstance(realmConfiguration)
            LocalRepositoryImpl(realm)
        }

        // Domain
        single { GetCacheObject(get()) }
        single { PutCacheObject(get()) }

        // Presentation
        single { (activity: BaseActivity) ->  SplashScreenPresenter(Navigator(activity)) }
    }

}