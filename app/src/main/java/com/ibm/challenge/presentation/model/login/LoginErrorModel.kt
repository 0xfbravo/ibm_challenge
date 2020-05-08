package com.ibm.challenge.presentation.model.login

import com.ibm.challenge.core.model.PresentationModel

data class LoginErrorModel(val code: Long? = null,
                           val message: String? = null): PresentationModel()