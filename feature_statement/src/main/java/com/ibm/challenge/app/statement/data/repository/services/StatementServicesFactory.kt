package com.ibm.challenge.app.statement.data.repository.services

import com.ibm.challenge.data.core.RequestManager

object StatementServicesFactory {

    private var statementServices: StatementServices? = null

    fun getStatementServices(baseUrl: String): StatementServices {
        if (statementServices == null) {
            statementServices = RequestManager.provideRetrofit(baseUrl).create(StatementServices::class.java)
        }

        return statementServices!!
    }

}