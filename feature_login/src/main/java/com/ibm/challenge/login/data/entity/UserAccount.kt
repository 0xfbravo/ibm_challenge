package com.ibm.challenge.login.data.entity

import com.ibm.challenge.data.entity.Entity
import com.ibm.challenge.login.domain.model.UserAccountDomain

data class UserAccount(val userID: Long? = null,
                       val name: String? = null,
                       val bankAccount: String? = null,
                       val agency: String? = null,
                       val balance: Double? = null): Entity<UserAccountDomain>() {

    companion object {
        fun fromDomainModel(domainModel: UserAccountDomain): UserAccount {
            return UserAccount(
                domainModel.userID,
                domainModel.name,
                domainModel.bankAccount,
                domainModel.agency,
                domainModel.balance
            )
        }
    }

    override fun asDomainModel(): UserAccountDomain {
        return UserAccountDomain(userID, name, bankAccount, agency, balance)
    }
}