package com.ibm.challenge.app.statement

import com.ibm.challenge.app.statement.data.repository.StatementRemoteRepositoryImpl
import com.ibm.challenge.app.statement.domain.interactors.GetStatements
import com.ibm.challenge.app.statement.domain.repository.StatementRemoteRepository
import org.koin.dsl.module

object FeatureStatementModule {

    fun get() = module {
        // Data
        single<StatementRemoteRepository> { StatementRemoteRepositoryImpl() }

        // Domain
        single { GetStatements(get(), get()) }

        // Presentation

    }

}