package com.ibm.challenge.login.data.repository.services

import com.ibm.challenge.data.core.RequestManager

object LoginServicesFactory {

    private var loginServices: LoginServices? = null

    fun getLoginServices(): LoginServices {
        if (loginServices == null) {
            loginServices = RequestManager.provideRetrofit().create(LoginServices::class.java)
        }

        return loginServices!!
    }

}