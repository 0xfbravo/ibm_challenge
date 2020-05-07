package com.ibm.challenge.domain.repository

import com.ibm.challenge.core.model.DomainModel
import org.joda.time.DateTime

interface LocalRepository {

    fun getObject(cacheKey: String): DomainModel
    fun getList(cacheKey: String): List<DomainModel>
    fun putObject(model: DomainModel, override: Boolean, cacheLimitDate: DateTime): Boolean
    fun putList(cacheKey: String, modelList: List<DomainModel>, override: Boolean, cacheLimitDate: DateTime): Boolean
    fun delete(cacheKey: String): Boolean

}