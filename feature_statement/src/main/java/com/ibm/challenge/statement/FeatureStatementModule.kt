package com.ibm.challenge.statement

import com.ibm.challenge.statement.data.repository.StatementRemoteRepositoryImpl
import com.ibm.challenge.statement.domain.interactors.GetStatements
import com.ibm.challenge.statement.domain.repository.StatementRemoteRepository
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