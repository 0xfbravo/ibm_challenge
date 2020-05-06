package com.ibm.challenge.login

import com.ibm.challenge.login.data.repository.LoginRemoteRepositoryImpl
import com.ibm.challenge.login.domain.interactors.PostLogin
import com.ibm.challenge.login.domain.interactors.ValidateCpf
import com.ibm.challenge.login.domain.interactors.ValidateEmail
import com.ibm.challenge.login.domain.interactors.ValidatePassword
import com.ibm.challenge.login.domain.repository.LoginRemoteRepository
import org.koin.dsl.module

object FeatureLoginModule {

    fun get() = module {
        // Data
        single<LoginRemoteRepository> { LoginRemoteRepositoryImpl() }

        // Domain
        single { ValidateCpf() }
        single { ValidateEmail() }
        single { ValidatePassword() }
        single { PostLogin(get(), get(), get(), get(), get()) }

        // Presentation

    }

}