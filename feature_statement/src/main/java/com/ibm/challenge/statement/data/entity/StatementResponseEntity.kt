package com.ibm.challenge.statement.data.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ibm.challenge.core.model.Entity

data class StatementResponseEntity(@Expose @SerializedName("statementList") val statementList: List<StatementEntity>? = null,
                                   @Expose @SerializedName("error") val error: ErrorEntity? = null): Entity()