package com.ibm.challenge.login.core

import com.ibm.challenge.login.data.entity.LoginErrorEntity
import com.ibm.challenge.login.data.entity.LoginResponseEntity
import com.ibm.challenge.login.data.entity.UserAccountEntity
import com.ibm.challenge.login.domain.model.LoginErrorDomain
import com.ibm.challenge.login.domain.model.LoginResponseDomain
import com.ibm.challenge.login.domain.model.UserAccountDomain

object DomainModelMapper {

    fun mapLoginResponse(entity: LoginResponseEntity): LoginResponseDomain {
        val statementListModel = if (entity.userAccount != null) mapUserAccount(entity.userAccount) else null
        val errorModel = if (entity.error != null) mapError(entity.error) else null
        return LoginResponseDomain(statementListModel, errorModel)
    }

    private fun mapUserAccount(entity: UserAccountEntity): UserAccountDomain {
        return UserAccountDomain(entity.userID, entity.name, entity.bankAccount, entity.agency, entity.balance)
    }

    private fun mapError(entity: LoginErrorEntity): LoginErrorDomain {
        return LoginErrorDomain(entity.code, entity.message)
    }

}