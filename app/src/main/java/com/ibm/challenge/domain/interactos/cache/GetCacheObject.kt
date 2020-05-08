package com.ibm.challenge.domain.interactos.cache

import com.ibm.challenge.core.model.DomainModel
import com.ibm.challenge.domain.core.CacheHelper
import com.ibm.challenge.domain.exceptions.InvalidCacheHelperException
import com.ibm.challenge.domain.exceptions.InvalidCacheKeyException
import com.ibm.challenge.domain.exceptions.ObjectIsNotInCacheException
import com.ibm.challenge.domain.interactos.Interactor
import com.ibm.challenge.domain.repository.LocalRepository

class GetCacheObject(private val localRepository: LocalRepository): Interactor<DomainModel>() {

    private var cacheHelper: CacheHelper? = null

    @Throws(InvalidCacheKeyException::class, ObjectIsNotInCacheException::class)
    override fun execute(): DomainModel {
        if (cacheHelper == null)
            throw InvalidCacheHelperException("O cache helper não pode ser nulo.")

        if (cacheHelper?.key == null)
            throw InvalidCacheKeyException("A chave de cache está vazia.")

        return localRepository.getObject(cacheHelper!!)
    }

    fun withParams(cacheHelper: CacheHelper): GetCacheObject {
        this.cacheHelper = cacheHelper
        return this
    }

    companion object {
        const val tag = "get_cache_object"
    }

}