package com.ibm.challenge.core.model

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import org.joda.time.DateTime
import java.util.*

@RealmClass
open class DomainModel: RealmModel {
    @PrimaryKey
    open var baseCacheKey: String? = null
    open var cacheLimitDate: Date? = null
}