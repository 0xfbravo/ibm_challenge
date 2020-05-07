package com.ibm.challenge.login.core

import com.ibm.challenge.login.domain.model.ErrorDomain
import com.ibm.challenge.login.domain.model.LoginResponseDomain
import com.ibm.challenge.login.domain.model.UserAccountDomain
import com.ibm.challenge.presentation.model.ErrorModel
import com.ibm.challenge.presentation.model.login.LoginResponseModel
import com.ibm.challenge.presentation.model.login.UserAccountModel

object PresentationModelMapper {

    fun mapLoginResponse(entity: LoginResponseDomain): LoginResponseModel {
        val statementListModel = if (entity.userAccount != null) mapUserAccount(entity.userAccount) else null
        val errorModel = if (entity.error != null) mapError(entity.error) else null
        return LoginResponseModel(statementListModel, errorModel)
    }

    private fun mapUserAccount(entity: UserAccountDomain): UserAccountModel {
        return UserAccountModel(entity.userID, entity.name, entity.bankAccount, entity.agency, entity.balance)
    }

    private fun mapError(entity: ErrorDomain): ErrorModel {
        return ErrorModel(entity.code, entity.message)
    }

}