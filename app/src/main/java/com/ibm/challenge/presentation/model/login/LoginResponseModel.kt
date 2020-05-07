package com.ibm.challenge.presentation.model.login

import com.ibm.challenge.core.model.PresentationModel

data class LoginResponseModel(val userAccount: UserAccountModel? = null,
                              val error: LoginErrorModel? = null): PresentationModel()