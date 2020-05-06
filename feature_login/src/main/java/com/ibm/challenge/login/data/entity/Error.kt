package com.ibm.challenge.login.data.entity

import com.ibm.challenge.data.entity.Entity
import com.ibm.challenge.login.domain.model.ErrorDomain

data class Error(val code: Long? = null,
                 val message: String? = null): Entity<ErrorDomain>() {

    companion object {
        fun fromDomainModel(domainModel: ErrorDomain): Error {
            return Error(domainModel.code, domainModel.message)
        }
    }

    override fun asDomainModel(): ErrorDomain {
        return ErrorDomain(code, message)
    }

}