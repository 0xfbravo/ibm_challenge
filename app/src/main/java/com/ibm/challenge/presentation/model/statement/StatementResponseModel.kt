package com.ibm.challenge.presentation.model.statement

import com.ibm.challenge.core.model.PresentationModel

data class StatementResponseModel(val statementList: List<StatementModel>? = null,
                                  val error: StatementErrorModel? = null): PresentationModel()