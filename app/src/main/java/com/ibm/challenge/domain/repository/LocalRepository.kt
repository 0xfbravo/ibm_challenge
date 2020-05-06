package com.ibm.challenge.domain.repository

import com.ibm.challenge.domain.model.DomainModel
import org.joda.time.DateTime

interface LocalRepository {

    fun getObject(cacheKey: String, id: String): DomainModel
    fun getList(cacheKey: String, id: String): List<DomainModel>
    fun putObject(cacheKey: String, model: DomainModel, override: Boolean, cacheLimitDate: DateTime): Boolean
    fun putList(cacheKey: String, modelList: List<DomainModel>, override: Boolean, cacheLimitDate: DateTime): Boolean
    fun delete(cacheKey: String): Boolean

}