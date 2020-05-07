package com.ibm.challenge.app.statement.domain.model

import com.ibm.challenge.domain.model.DomainModel

data class StatementDomain(val title: String? = null,
                           val description: String? = null,
                           val date: String? = null,
                           val value: Double? = null): DomainModel()