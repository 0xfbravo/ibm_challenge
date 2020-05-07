package com.ibm.challenge.app.login.data.repository

import com.ibm.challenge.data.core.RequestManager
import com.ibm.challenge.app.login.data.entity.LoginRequestBodyEntity
import com.ibm.challenge.app.login.data.repository.services.LoginServicesFactory
import com.ibm.challenge.app.login.domain.interactors.PostLogin
import com.ibm.challenge.app.login.domain.model.LoginResponseDomain
import com.ibm.challenge.app.login.domain.repository.LoginRemoteRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class LoginRemoteRepositoryImpl(private val baseURL: String = RequestManager.baseUrl): LoginRemoteRepository {

    override fun postLogin(user: String, password: String): Single<LoginResponseDomain> {
        val requestBody = LoginRequestBodyEntity(user, password)
        return LoginServicesFactory
            .getLoginServices(baseURL)
            .postLogin(requestBody)
            .doOnError { onRequestError(PostLogin.tag, it.localizedMessage!!) }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.asDomainModel() }
    }

}