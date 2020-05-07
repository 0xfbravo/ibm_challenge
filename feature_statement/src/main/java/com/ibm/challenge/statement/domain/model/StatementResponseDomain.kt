package com.ibm.challenge.statement.domain.model

import com.ibm.challenge.core.model.DomainModel

data class StatementResponseDomain(val statementList: List<StatementDomain>? = null,
                                   val error: StatementErrorDomain? = null): DomainModel()