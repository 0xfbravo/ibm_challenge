package com.ibm.challenge.statement.data.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ibm.challenge.core.model.Entity

data class StatementEntity(@Expose @SerializedName("title") val title: String? = null,
                           @Expose @SerializedName("description") val description: String? = null,
                           @Expose @SerializedName("date") val date: String? = null,
                           @Expose @SerializedName("value") val value: Double? = null): Entity()