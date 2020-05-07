package com.ibm.challenge.app.login.data.repository.services

import com.ibm.challenge.data.core.RequestManager

object LoginServicesFactory {

    private var loginServices: LoginServices? = null

    fun getLoginServices(baseUrl: String): LoginServices {
        if (loginServices == null) {
            loginServices = RequestManager.provideRetrofit(baseUrl).create(LoginServices::class.java)
        }

        return loginServices!!
    }

}