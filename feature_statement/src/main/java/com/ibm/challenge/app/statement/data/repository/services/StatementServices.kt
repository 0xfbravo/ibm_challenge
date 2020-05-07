package com.ibm.challenge.statement.data.repository.services

import com.ibm.challenge.statement.data.entity.StatementResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.POST
import retrofit2.http.Path

interface StatementServices {

    @POST("statement/{userId}")
    fun getStatements(
        @Path("userId") userId: Long
    ): Single<StatementResponse>

}