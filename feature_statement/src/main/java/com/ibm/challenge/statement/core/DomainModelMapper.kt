package com.ibm.challenge.statement.core

import com.ibm.challenge.statement.data.entity.StatementErrorEntity
import com.ibm.challenge.statement.data.entity.StatementEntity
import com.ibm.challenge.statement.data.entity.StatementResponseEntity
import com.ibm.challenge.statement.domain.model.StatementErrorDomain
import com.ibm.challenge.statement.domain.model.StatementDomain
import com.ibm.challenge.statement.domain.model.StatementResponseDomain

object DomainModelMapper {

    fun mapStatementResponse(entity: StatementResponseEntity): StatementResponseDomain {
        val statementListModel = if (entity.statementList != null) mapStatementList(entity.statementList) else null
        val errorModel = if (entity.error != null) mapError(entity.error) else null
        return StatementResponseDomain(statementListModel, errorModel)
    }

    private fun mapStatementList(entityList: List<StatementEntity>): List<StatementDomain> {
        return entityList.map { mapStatement(it) }.toList()
    }

    private fun mapStatement(entity: StatementEntity): StatementDomain {
        return StatementDomain(entity.title, entity.description, entity.date, entity.value)
    }

    private fun mapError(entity: StatementErrorEntity): StatementErrorDomain {
        return StatementErrorDomain(entity.code, entity.message)
    }

}