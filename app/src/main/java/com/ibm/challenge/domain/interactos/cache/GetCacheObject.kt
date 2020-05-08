package com.ibm.challenge.domain.interactos.cache

import com.ibm.challenge.core.model.DomainModel
import com.ibm.challenge.domain.core.CacheHelper
import com.ibm.challenge.domain.exceptions.InvalidCacheHelperException
import com.ibm.challenge.domain.exceptions.InvalidCacheKeyException
import com.ibm.challenge.domain.exceptions.InvalidDomainModelException
import com.ibm.challenge.domain.exceptions.ObjectIsNotInCacheException
import com.ibm.challenge.domain.interactos.Interactor
import com.ibm.challenge.domain.repository.local.LocalRepository

class GetCacheObject(private val localRepository: LocalRepository): Interactor<DomainModel>() {

    private var cacheHelper: CacheHelper? = null
    private var cacheObjectClass: Class<out DomainModel>? = null

    @Throws(InvalidCacheKeyException::class, ObjectIsNotInCacheException::class, InvalidDomainModelException::class)
    override fun execute(): DomainModel {
        if (cacheHelper == null)
            throw InvalidCacheHelperException("O cache helper não pode ser nulo.")

        if (cacheHelper?.key.isNullOrEmpty())
            throw InvalidCacheKeyException("A chave de cache não pode ser vazia.")

        if (cacheObjectClass == null)
            throw InvalidDomainModelException("A classe de desserialização não pode ser nula.")

        return localRepository.getObject(cacheHelper!!, cacheObjectClass!!)
    }

    fun withParams(cacheHelper: CacheHelper, cacheObjectClass: Class<out DomainModel>): GetCacheObject {
        this.cacheHelper = cacheHelper
        this.cacheObjectClass = cacheObjectClass
        return this
    }

    companion object {
        const val tag = "get_cache_object"
    }

}