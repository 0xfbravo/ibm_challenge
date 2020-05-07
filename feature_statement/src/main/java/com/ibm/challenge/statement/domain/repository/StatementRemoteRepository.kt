package com.ibm.challenge.statement.domain.repository

import com.ibm.challenge.domain.repository.RemoteRepository
import com.ibm.challenge.statement.domain.model.StatementResponseDomain
import io.reactivex.rxjava3.core.Single

interface StatementRemoteRepository: RemoteRepository {

    fun getStatements(userId: Long): Single<StatementResponseDomain>

}