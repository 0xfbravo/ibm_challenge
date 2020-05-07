package com.ibm.challenge.statement.domain.model

import com.ibm.challenge.domain.model.DomainModel

data class ErrorDomain(val code: Long? = null,
                       val message: String? = null): DomainModel()