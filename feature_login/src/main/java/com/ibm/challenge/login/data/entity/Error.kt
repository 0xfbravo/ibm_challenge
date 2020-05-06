package com.ibm.challenge.login.data.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ibm.challenge.data.entity.Entity
import com.ibm.challenge.login.domain.model.ErrorDomain

data class Error(@Expose @SerializedName("code") val code: Long? = null,
                 @Expose @SerializedName("message") val message: String? = null): Entity<ErrorDomain>() {

    companion object {
        fun fromDomainModel(domainModel: ErrorDomain): Error {
            return Error(domainModel.code, domainModel.message)
        }
    }

    override fun asDomainModel(): ErrorDomain {
        return ErrorDomain(code, message)
    }

}