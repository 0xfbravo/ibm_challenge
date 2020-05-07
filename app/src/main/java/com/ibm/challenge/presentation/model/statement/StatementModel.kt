package com.ibm.challenge.presentation.model.statement

import com.ibm.challenge.core.model.PresentationModel
import java.util.*

data class StatementModel(val title: String? = null,
                          val description: String? = null,
                          val date: Date? = null,
                          val value: Double? = null): PresentationModel()