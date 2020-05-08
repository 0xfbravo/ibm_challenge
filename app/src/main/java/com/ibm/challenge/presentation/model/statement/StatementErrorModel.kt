package com.ibm.challenge.presentation.model.statement

import com.ibm.challenge.core.model.PresentationModel

data class StatementErrorModel(val code: Long? = null,
                               val message: String? = null): PresentationModel()