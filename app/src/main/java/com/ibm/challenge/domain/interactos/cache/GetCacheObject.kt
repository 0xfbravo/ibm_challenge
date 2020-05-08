package com.ibm.challenge.domain.interactos.cache

import com.ibm.challenge.core.model.DomainModel
import com.ibm.challenge.domain.exceptions.InvalidCacheKeyException
import com.ibm.challenge.domain.exceptions.ObjectIsNotInCacheException
import com.ibm.challenge.domain.interactos.Interactor
import com.ibm.challenge.domain.repository.LocalRepository

class GetCacheObject(private val localRepository: LocalRepository): Interactor<DomainModel>() {

    private var cacheKey: String = ""

    @Throws(InvalidCacheKeyException::class, ObjectIsNotInCacheException::class)
    override fun execute(): DomainModel {
        if (cacheKey.isEmpty())
            throw InvalidCacheKeyException("A chave de cache está vazia.")

        return localRepository.getObject(cacheKey)
    }

    fun withParams(cacheKey: String): GetCacheObject {
        this.cacheKey = cacheKey
        return this
    }

    companion object {
        const val tag = "get_cache_object"
    }

}