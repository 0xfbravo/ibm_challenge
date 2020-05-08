package com.ibm.challenge.domain.repository

import com.ibm.challenge.core.model.DomainModel
import com.ibm.challenge.domain.core.CacheHelper
import com.ibm.challenge.domain.exceptions.InvalidCacheKeyException
import com.ibm.challenge.domain.exceptions.ObjectIsNotInCacheException

interface LocalRepository {

    @Throws(ObjectIsNotInCacheException::class)
    fun getObject(cacheHelper: CacheHelper): DomainModel

    @Throws(ObjectIsNotInCacheException::class)
    fun getList(cacheHelper: CacheHelper): List<DomainModel>

    @Throws(InvalidCacheKeyException::class)
    fun putObject(cacheHelper: CacheHelper, model: DomainModel): Boolean

    @Throws(InvalidCacheKeyException::class)
    fun putList(cacheHelper: CacheHelper, modelList: List<DomainModel>): Boolean

    @Throws(InvalidCacheKeyException::class)
    fun delete(cacheHelper: CacheHelper): Boolean

}