package com.ibm.challenge.statement.data.repository

import com.ibm.challenge.data.core.RequestManager
import com.ibm.challenge.statement.data.repository.services.StatementServicesFactory
import com.ibm.challenge.statement.domain.interactors.GetStatements
import com.ibm.challenge.statement.domain.model.StatementResponseDomain
import com.ibm.challenge.statement.domain.repository.StatementRemoteRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class StatementRemoteRepositoryImpl(private val baseURL: String = RequestManager.baseUrl): StatementRemoteRepository {

    override fun getStatements(userId: Long): Single<StatementResponseDomain> {
        return StatementServicesFactory
            .getStatementServices(baseURL)
            .getStatements(userId)
            .doOnError { onRequestError(GetStatements.tag, it.localizedMessage!!) }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.asDomainModel() }
    }
}