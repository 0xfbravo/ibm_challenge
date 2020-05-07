package com.ibm.challenge.presentation.model.login

import com.ibm.challenge.core.model.PresentationModel
import com.ibm.challenge.presentation.model.ErrorModel

data class LoginResponseModel(val userAccount: UserAccountModel? = null,
                              val error: ErrorModel? = null): PresentationModel()