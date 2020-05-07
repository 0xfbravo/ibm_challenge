package com.ibm.challenge.presentation.model

import com.ibm.challenge.core.model.PresentationModel

data class ErrorModel(val code: Long? = null,
                      val message: String? = null): PresentationModel()