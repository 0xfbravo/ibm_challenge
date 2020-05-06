package com.ibm.challenge.login.presentation.model

import com.ibm.challenge.login.domain.model.UserAccountDomain
import com.ibm.challenge.presentation.core.mvp.PresentationModel

data class UserAccountModel(val userID: Long? = null,
                            val name: String? = null,
                            val bankAccount: String? = null,
                            val agency: String? = null,
                            val balance: Double? = null): PresentationModel<UserAccountDomain>() {

    override fun asDomainModel(): UserAccountDomain {
        TODO("Not yet implemented")
    }

    companion object {
        fun fromDomainModel(domain: UserAccountDomain): UserAccountModel {
            return UserAccountModel(domain.userID, domain.name, domain.bankAccount, domain.agency, domain.balance)
        }
    }

}