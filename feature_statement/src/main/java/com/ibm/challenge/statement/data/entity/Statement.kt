package com.ibm.challenge.statement.data.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ibm.challenge.data.entity.Entity
import com.ibm.challenge.statement.domain.model.StatementDomain

data class Statement(@Expose @SerializedName("title") val title: String? = null,
                     @Expose @SerializedName("description") val description: String? = null,
                     @Expose @SerializedName("date") val date: String? = null,
                     @Expose @SerializedName("value") val value: Double? = null): Entity<StatementDomain>() {

    override fun asDomainModel(): StatementDomain {
        return StatementDomain(title, description, date, value)
    }

}