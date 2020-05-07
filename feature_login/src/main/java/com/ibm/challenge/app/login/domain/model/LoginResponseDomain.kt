package com.ibm.challenge.app.login.domain.model

import com.ibm.challenge.domain.model.DomainModel

data class LoginResponseDomain(val userAccount: UserAccountDomain? = null,
                               val error: ErrorDomain? = null): DomainModel()