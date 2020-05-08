package com.ibm.challenge.login.data.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ibm.challenge.core.model.Entity

data class LoginRequestBodyEntity(@Expose @SerializedName("user") val user: String,
                                  @Expose @SerializedName("password") val password: String): Entity()