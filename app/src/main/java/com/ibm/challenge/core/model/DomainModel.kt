package com.ibm.challenge.core.model

import org.joda.time.DateTime

abstract class DomainModel {
    var cachedAt: DateTime? = null
}