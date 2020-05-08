package com.ibm.challenge.domain.interactos.cache

import com.ibm.challenge.core.model.DomainModel
import com.ibm.challenge.domain.exceptions.InvalidDomainModelException
import com.ibm.challenge.domain.interactos.Interactor
import com.ibm.challenge.domain.repository.LocalRepository

class PutCacheObject(private val localRepository: LocalRepository): Interactor<Boolean>() {

    private var domainModel: DomainModel? = null

    @Throws(InvalidDomainModelException::class)
    override fun execute(): Boolean {
        if (domainModel == null)
            throw InvalidDomainModelException("O modelo é nulo.")

        return localRepository.putObject(domainModel!!)
    }

    fun withParams(domainModel: DomainModel): PutCacheObject {
        this.domainModel = domainModel
        return this
    }

    companion object {
        const val tag = "put_cache_object"
    }

}