package com.ibm.challenge.app.statement.data.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ibm.challenge.data.entity.Entity
import com.ibm.challenge.app.statement.domain.model.StatementResponseDomain

data class StatementResponse(@Expose @SerializedName("statementList") val statement: List<Statement>? = null,
                             @Expose @SerializedName("error") val error: Error? = null): Entity<StatementResponseDomain>() {

    override fun asDomainModel(): StatementResponseDomain {
        val statementDomainList = statement?.map { it.asDomainModel() }?.toList()
        return StatementResponseDomain(statementDomainList, error?.asDomainModel())
    }

}