package com.ibm.challenge.app.login.data.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ibm.challenge.data.entity.Entity
import com.ibm.challenge.app.login.domain.model.LoginResponseDomain

data class LoginResponseEntity(@Expose @SerializedName("userAccount") val userAccount: UserAccountEntity? = null,
                               @Expose @SerializedName("error") val error: ErrorEntity? = null): Entity<LoginResponseDomain>() {

    override fun asDomainModel(): LoginResponseDomain {
        return LoginResponseDomain(userAccount?.asDomainModel(), error?.asDomainModel())
    }

}