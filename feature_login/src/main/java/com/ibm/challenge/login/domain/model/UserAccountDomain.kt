package com.ibm.challenge.login.domain.model

import com.ibm.challenge.domain.model.DomainModel

data class UserAccountDomain(val userID: Long? = null,
                             val name: String? = null,
                             val bankAccount: String? = null,
                             val agency: String? = null,
                             val balance: Double? = null): DomainModel()