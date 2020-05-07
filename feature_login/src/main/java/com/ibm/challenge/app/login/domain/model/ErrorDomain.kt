package com.ibm.challenge.app.login.domain.model

import com.ibm.challenge.domain.model.DomainModel

data class ErrorDomain(val code: Long? = null,
                       val message: String? = null): DomainModel()