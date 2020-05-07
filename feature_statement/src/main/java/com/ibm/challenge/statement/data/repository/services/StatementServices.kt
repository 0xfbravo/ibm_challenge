package com.ibm.challenge.statement.data.repository.services

import com.ibm.challenge.statement.data.entity.StatementResponseEntity
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface StatementServices {

    @GET("statements/{userId}")
    fun getStatements(
        @Path("userId") userId: Long
    ): Single<StatementResponseEntity>

}