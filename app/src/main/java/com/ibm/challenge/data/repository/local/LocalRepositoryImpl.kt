package com.ibm.challenge.data.repository.local

import com.ibm.challenge.data.exceptions.ObjectIsntInCacheException
import com.ibm.challenge.domain.model.DomainModel
import com.ibm.challenge.domain.repository.LocalRepository
import io.realm.Realm
import org.joda.time.DateTime

class LocalRepositoryImpl(private val realm: Realm): LocalRepository {

    override fun getObject(cacheKey: String): DomainModel {
        try {
            val result = realm.where(DomainModel::class.java).equalTo("cacheKey", cacheKey)
            return result.findFirst()
        }
        catch (e : Exception) {
            e.printStackTrace()
            throw ObjectIsntInCacheException("Não existe nenhum objeto com a chave [$cacheKey] em cache.")
        }
    }

    override fun getList(cacheKey: String): List<DomainModel> {
        TODO("Not yet implemented")
    }

    override fun putObject(model: DomainModel, override: Boolean, cacheLimitDate: DateTime): Boolean {
        model.cacheLimitDate = cacheLimitDate.toDate()

        return try {
            realm.beginTransaction()
            realm.copyToRealmOrUpdate(model)
            realm.commitTransaction()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override fun putList(
        cacheKey: String,
        modelList: List<DomainModel>,
        override: Boolean,
        cacheLimitDate: DateTime
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun delete(cacheKey: String): Boolean {
        return try {
            val results = realm.where(DomainModel::class.java).equalTo("cacheKey", cacheKey).findAll()
            results.deleteAllFromRealm()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}