package com.ibm.challenge.app.statement.data.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ibm.challenge.data.entity.Entity
import com.ibm.challenge.app.statement.domain.model.ErrorDomain

data class Error(@Expose @SerializedName("code") val code: Long? = null,
                 @Expose @SerializedName("message") val message: String? = null): Entity<ErrorDomain>() {

    override fun asDomainModel(): ErrorDomain {
        return ErrorDomain(code, message)
    }

}