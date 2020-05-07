package com.ibm.challenge.presentation.model.statement

import com.ibm.challenge.core.model.PresentationModel

data class StatementModel(val title: String? = null,
                          val description: String? = null,
                          val date: String? = null,
                          val value: Double? = null): PresentationModel()