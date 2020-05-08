package com.ibm.challenge.domain.interactos.cache

import com.ibm.challenge.core.model.DomainModel
import com.ibm.challenge.domain.core.CacheHelper
import com.ibm.challenge.domain.exceptions.InvalidCacheHelperException
import com.ibm.challenge.domain.exceptions.InvalidCacheKeyException
import com.ibm.challenge.domain.exceptions.InvalidDomainModelException
import com.ibm.challenge.domain.interactos.Interactor
import com.ibm.challenge.domain.repository.LocalRepository

class PutCacheObject(private val localRepository: LocalRepository): Interactor<Boolean>() {

    private var cacheHelper: CacheHelper? = null
    private var domainModel: DomainModel? = null

    @Throws(InvalidDomainModelException::class, InvalidCacheKeyException::class)
    override fun execute(): Boolean {
        if (cacheHelper == null)
            throw InvalidCacheHelperException("O cache helper não pode ser nulo.")

        if (cacheHelper?.key == null)
            throw InvalidCacheKeyException("A chave de cache está vazia.")

        if (domainModel == null)
            throw InvalidDomainModelException("O modelo não pode ser nulo.")

        return localRepository.putObject(cacheHelper!!, domainModel!!)
    }

    fun withParams(cacheHelper: CacheHelper, domainModel: DomainModel): PutCacheObject {
        this.cacheHelper = cacheHelper
        this.domainModel = domainModel
        return this
    }

    companion object {
        const val tag = "put_cache_object"
    }

}