package com.ibm.challenge.app.login.data.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ibm.challenge.data.entity.Entity
import com.ibm.challenge.domain.model.DomainModel

data class LoginRequestBodyEntity(@Expose @SerializedName("user") val user: String,
                                  @Expose @SerializedName("password") val password: String): Entity<DomainModel>() {

    override fun asDomainModel(): DomainModel {
        TODO("Not yet implemented")
    }
}