package com.ibm.challenge.statement.data.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ibm.challenge.core.model.Entity
import java.util.*

data class StatementEntity(@Expose @SerializedName("title") val title: String? = null,
                           @Expose @SerializedName("desc") val description: String? = null,
                           @Expose @SerializedName("date") val date: Date? = null,
                           @Expose @SerializedName("value") val value: Double? = null): Entity()