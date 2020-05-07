package com.ibm.challenge.presentation.model.statement

import com.ibm.challenge.core.model.PresentationModel
import com.ibm.challenge.presentation.model.ErrorModel

data class StatementResponseModel(val statementList: List<StatementModel>? = null,
                                  val error: ErrorModel? = null): PresentationModel()