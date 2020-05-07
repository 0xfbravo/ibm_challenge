package com.ibm.challenge.statement.domain.model

import com.ibm.challenge.core.model.DomainModel
import java.util.*

data class StatementDomain(val title: String? = null,
                           val description: String? = null,
                           val date: Date? = null,
                           val value: Double? = null): DomainModel()