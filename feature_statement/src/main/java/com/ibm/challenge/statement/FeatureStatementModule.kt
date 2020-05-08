package com.ibm.challenge.statement

import com.ibm.challenge.core.Navigator
import com.ibm.challenge.core.mvp.BaseActivity
import com.ibm.challenge.statement.data.repository.StatementRemoteRepositoryImpl
import com.ibm.challenge.statement.domain.interactors.GetStatements
import com.ibm.challenge.statement.domain.repository.StatementRemoteRepository
import com.ibm.challenge.statement.presentation.view.StatementPresenter
import org.koin.dsl.module

object FeatureStatementModule {

    fun get() = module(override = true) {
        // Data
        single<StatementRemoteRepository> { StatementRemoteRepositoryImpl() }

        // Domain
        single { GetStatements(get(), get()) }

        // Presentation
        single { (activity: BaseActivity) -> StatementPresenter(
            Navigator(
                activity
            ), get()) }
    }

}