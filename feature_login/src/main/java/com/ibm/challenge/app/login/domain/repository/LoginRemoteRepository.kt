package com.ibm.challenge.login.domain.repository

import com.ibm.challenge.domain.repository.RemoteRepository
import com.ibm.challenge.login.domain.model.LoginResponseDomain
import io.reactivex.rxjava3.core.Single

interface LoginRemoteRepository: RemoteRepository {

    fun postLogin(user: String, password: String): Single<LoginResponseDomain>

}