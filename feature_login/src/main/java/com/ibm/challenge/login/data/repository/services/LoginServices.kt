package com.ibm.challenge.login.data.repository.services

import com.ibm.challenge.login.data.entity.LoginRequestBodyEntity
import com.ibm.challenge.login.data.entity.LoginResponseEntity
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginServices {

    @POST("login")
    fun postLogin(
        @Body requestBody: LoginRequestBodyEntity
    ): Single<LoginResponseEntity>

}