package com.ibm.challenge.login.data.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ibm.challenge.data.entity.Entity
import com.ibm.challenge.login.domain.model.UserAccountDomain

data class UserAccount(@Expose @SerializedName("userId") val userID: Long? = null,
                       @Expose @SerializedName("name") val name: String? = null,
                       @Expose @SerializedName("bankAccount") val bankAccount: String? = null,
                       @Expose @SerializedName("agency") val agency: String? = null,
                       @Expose @SerializedName("balance") val balance: Double? = null): Entity<UserAccountDomain>() {

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