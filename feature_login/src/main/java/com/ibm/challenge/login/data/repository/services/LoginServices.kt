package com.ibm.challenge.login.data.repository.services

import com.ibm.challenge.login.data.entity.LoginRequestBody
import com.ibm.challenge.login.data.entity.LoginResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginServices {

    @POST("login")
    fun postLogin(
        @Body requestBody: LoginRequestBody
    ): Single<LoginResponse>

}