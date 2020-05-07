package com.ibm.challenge.login.domain.model

import com.ibm.challenge.core.model.DomainModel

data class LoginErrorDomain(val code: Long? = null,
                            val message: String? = null): DomainModel()