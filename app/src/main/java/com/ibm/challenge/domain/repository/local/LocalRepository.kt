package com.ibm.challenge.domain.repository.local

import com.ibm.challenge.core.model.DomainModel
import com.ibm.challenge.domain.core.CacheHelper
import com.ibm.challenge.domain.exceptions.InvalidCacheKeyException
import com.ibm.challenge.domain.exceptions.ObjectIsNotInCacheException

interface LocalRepository {

    @Throws(ObjectIsNotInCacheException::class)
    fun getObject(cacheHelper: CacheHelper, cacheObjectClass: Class<out DomainModel>): DomainModel

    @Throws(ObjectIsNotInCacheException::class)
    fun getList(cacheHelper: CacheHelper, cacheObjectClass: Class<out DomainModel>): List<DomainModel>

    @Throws(InvalidCacheKeyException::class)
    fun putObject(cacheHelper: CacheHelper, model: DomainModel): Boolean

    @Throws(InvalidCacheKeyException::class)
    fun putList(cacheHelper: CacheHelper, modelList: List<DomainModel>): Boolean

    @Throws(InvalidCacheKeyException::class)
    fun delete(cacheHelper: CacheHelper): Boolean

}