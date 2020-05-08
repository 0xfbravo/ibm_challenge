package com.ibm.challenge.login.data.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ibm.challenge.core.model.Entity

data class UserAccountEntity(@Expose @SerializedName("userId") val userID: Long? = null,
                             @Expose @SerializedName("name") val name: String? = null,
                             @Expose @SerializedName("bankAccount") val bankAccount: String? = null,
                             @Expose @SerializedName("agency") val agency: String? = null,
                             @Expose @SerializedName("balance") val balance: Double? = null): Entity()