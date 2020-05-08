package com.ibm.challenge.login

import com.ibm.challenge.core.Navigator
import com.ibm.challenge.core.mvp.BaseActivity
import com.ibm.challenge.domain.interactos.cache.PutCacheObject
import com.ibm.challenge.login.data.repository.LoginRemoteRepositoryImpl
import com.ibm.challenge.login.domain.interactors.*
import com.ibm.challenge.login.domain.repository.LoginRemoteRepository
import com.ibm.challenge.login.presentation.view.LoginPresenter
import org.koin.dsl.module

object FeatureLoginModule {

    fun get() = module(override = true) {
        // Data
        single<LoginRemoteRepository> { LoginRemoteRepositoryImpl() }

        // Domain
        single { PutCacheObject(get()) }
        single { PostLogin(get(), get(), get(), get()) }
        single { ValidateCpf() }
        single { ValidateEmail() }
        single { ValidatePassword() }

        // Presentation
        single { (activity: BaseActivity) -> LoginPresenter(Navigator(activity), get(), get(), get(), get(), get(), get()) }
    }

}