package com.ibm.challenge.statement.core

import com.ibm.challenge.statement.data.entity.ErrorEntity
import com.ibm.challenge.statement.data.entity.StatementEntity
import com.ibm.challenge.statement.data.entity.StatementResponseEntity
import com.ibm.challenge.statement.domain.model.ErrorDomain
import com.ibm.challenge.statement.domain.model.StatementDomain
import com.ibm.challenge.statement.domain.model.StatementResponseDomain

object DomainModelMapper {

    fun mapStatementResponse(entity: StatementResponseEntity): StatementResponseDomain {
        val statementListModel = if (entity.statementList != null) mapStatementList(entity.statementList) else null
        val errorModel = if (entity.error != null) mapError(entity.error) else null
        return StatementResponseDomain(statementListModel, errorModel)
    }

    fun mapStatementList(entityList: List<StatementEntity>): List<StatementDomain> {
        return entityList.map { mapStatement(it) }.toList()
    }

    fun mapStatement(entity: StatementEntity): StatementDomain {
        return StatementDomain(entity.title, entity.description, entity.date, entity.value)
    }

    fun mapError(entity: ErrorEntity): ErrorDomain {
        return ErrorDomain(entity.code, entity.message)
    }

}