package com.ibm.challenge.domain.repository

import com.ibm.challenge.core.model.DomainModel
import com.ibm.challenge.domain.exceptions.ObjectIsNotInCacheException
import org.joda.time.DateTime

interface LocalRepository {

    @Throws(ObjectIsNotInCacheException::class)
    fun getObject(cacheKey: String): DomainModel

    @Throws(ObjectIsNotInCacheException::class)
    fun getList(cacheKey: String): List<DomainModel>

    fun putObject(model: DomainModel, cacheLimitDate: DateTime? = null): Boolean

    fun putList(cacheKey: String, modelList: List<DomainModel>, cacheLimitDate: DateTime? = null): Boolean

    fun delete(cacheKey: String): Boolean

}