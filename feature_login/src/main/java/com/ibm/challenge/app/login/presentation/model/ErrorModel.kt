package com.ibm.challenge.app.login.presentation.model

import com.ibm.challenge.app.login.domain.model.ErrorDomain
import com.ibm.challenge.presentation.core.mvp.PresentationModel

data class ErrorModel(val code: Long? = null,
                      val message: String? = null): PresentationModel<ErrorDomain>() {

    override fun asDomainModel(): ErrorDomain {
        TODO("Not yet implemented")
    }

    companion object {
        fun fromDomainModel(domain: ErrorDomain): ErrorModel {
            return ErrorModel(domain.code, domain.message)
        }
    }

}