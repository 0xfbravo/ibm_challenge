package com.ibm.challenge.login.domain.model

import com.ibm.challenge.core.model.DomainModel

data class LoginResponseDomain(val userAccount: UserAccountDomain? = null,
                               val error: LoginErrorDomain? = null): DomainModel()