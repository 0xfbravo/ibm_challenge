package com.ibm.challenge.statement.core

import com.ibm.challenge.statement.domain.model.ErrorDomain
import com.ibm.challenge.statement.domain.model.StatementDomain
import com.ibm.challenge.statement.domain.model.StatementResponseDomain
import com.ibm.challenge.presentation.model.ErrorModel
import com.ibm.challenge.presentation.model.statement.StatementModel
import com.ibm.challenge.presentation.model.statement.StatementResponseModel

object PresentationModelMapper {

    fun mapStatementResponse(domain: StatementResponseDomain): StatementResponseModel {
        val statementListModel = if (domain.statementList != null) mapStatementList(domain.statementList) else null
        val errorModel = if (domain.error != null) mapError(domain.error) else null
        return StatementResponseModel(statementListModel, errorModel)
    }

    fun mapStatementList(domainList: List<StatementDomain>): List<StatementModel> {
        return domainList.map { mapStatement(it) }.toList()
    }

    fun mapStatement(domain: StatementDomain): StatementModel {
        return StatementModel(domain.title, domain.description, domain.date, domain.value)
    }

    fun mapError(domain: ErrorDomain): ErrorModel {
        return ErrorModel(domain.code, domain.message)
    }

}