package com.ibm.challenge.app.login

import com.ibm.challenge.app.login.data.repository.LoginRemoteRepositoryImpl
import com.ibm.challenge.app.login.domain.interactors.PostLogin
import com.ibm.challenge.app.login.domain.interactors.ValidateCpf
import com.ibm.challenge.app.login.domain.interactors.ValidateEmail
import com.ibm.challenge.app.login.domain.interactors.ValidatePassword
import com.ibm.challenge.app.login.domain.repository.LoginRemoteRepository
import com.ibm.challenge.app.login.presentation.view.LoginPresenter
import com.ibm.challenge.presentation.core.Navigator
import com.ibm.challenge.presentation.core.mvp.BaseActivity
import org.koin.dsl.module

object FeatureLoginModule {

    fun get() = module(override = true) {
        // Data
        single<LoginRemoteRepository> { LoginRemoteRepositoryImpl() }

        // Domain
        single { ValidateCpf() }
        single { ValidateEmail() }
        single { ValidatePassword() }
        single { PostLogin(get(), get(), get(), get(), get()) }

        // Presentation
        single { (activity: BaseActivity) -> LoginPresenter(Navigator(activity), get(), get(), get(), get()) }
    }

}