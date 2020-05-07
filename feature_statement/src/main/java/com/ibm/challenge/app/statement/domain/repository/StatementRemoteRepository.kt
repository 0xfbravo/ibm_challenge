package com.ibm.challenge.app.statement.domain.repository

import com.ibm.challenge.domain.repository.RemoteRepository
import com.ibm.challenge.app.statement.domain.model.StatementResponseDomain
import io.reactivex.rxjava3.core.Single

interface StatementRemoteRepository: RemoteRepository {

    fun getStatements(userId: Long): Single<StatementResponseDomain>

}