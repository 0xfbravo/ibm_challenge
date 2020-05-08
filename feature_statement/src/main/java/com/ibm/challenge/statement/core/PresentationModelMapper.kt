package com.ibm.challenge.statement.core

import com.ibm.challenge.presentation.model.statement.StatementErrorModel
import com.ibm.challenge.presentation.model.statement.StatementModel
import com.ibm.challenge.presentation.model.statement.StatementResponseModel
import com.ibm.challenge.statement.domain.model.StatementDomain
import com.ibm.challenge.statement.domain.model.StatementErrorDomain
import com.ibm.challenge.statement.domain.model.StatementResponseDomain

object PresentationModelMapper {

    fun mapStatementResponse(domain: StatementResponseDomain): StatementResponseModel {
        val statementListModel = if (domain.statementList != null) mapStatementList(domain.statementList) else null
        val errorModel = if (domain.error != null) mapError(domain.error) else null
        return StatementResponseModel(statementListModel, errorModel)
    }

    private fun mapStatementList(domainList: List<StatementDomain>): List<StatementModel> {
        return domainList.map { mapStatement(it) }.toList()
    }

    private fun mapStatement(domain: StatementDomain): StatementModel {
        return StatementModel(domain.title, domain.description, domain.date, domain.value)
    }

    private fun mapError(domain: StatementErrorDomain): StatementErrorModel {
        return StatementErrorModel(
            domain.code,
            domain.message
        )
    }

}