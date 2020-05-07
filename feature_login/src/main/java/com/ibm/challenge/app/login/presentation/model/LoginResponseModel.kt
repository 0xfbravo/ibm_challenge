package com.ibm.challenge.app.login.presentation.model

import com.ibm.challenge.app.login.domain.model.LoginResponseDomain
import com.ibm.challenge.presentation.core.mvp.PresentationModel

data class LoginResponseModel(val userAccount: UserAccountModel? = null,
                              val error: ErrorModel? = null): PresentationModel<LoginResponseDomain>() {

    override fun asDomainModel(): LoginResponseDomain {
        TODO("Not yet implemented")
    }

    companion object {
        fun fromDomainModel(domain: LoginResponseDomain): LoginResponseModel {
            val userAccountModel = if (domain.userAccount != null) UserAccountModel.fromDomainModel(domain.userAccount) else null
            val errorModel = if (domain.error != null) ErrorModel.fromDomainModel(domain.error) else null
            return LoginResponseModel(userAccountModel, errorModel)
        }
    }

}