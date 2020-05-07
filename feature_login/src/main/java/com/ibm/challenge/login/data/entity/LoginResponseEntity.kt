package com.ibm.challenge.login.data.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ibm.challenge.core.model.Entity

data class LoginResponseEntity(@Expose @SerializedName("userAccount") val userAccount: UserAccountEntity? = null,
                               @Expose @SerializedName("error") val error: LoginErrorEntity? = null): Entity()