package com.ibm.challenge.data.repository.local

import com.ibm.challenge.core.model.DomainModel
import com.ibm.challenge.domain.exceptions.ObjectIsNotInCacheException
import com.ibm.challenge.domain.repository.LocalRepository
import io.paperdb.Book
import org.joda.time.DateTime

class LocalRepositoryImpl(private val paperBook: Book): LocalRepository {

    override fun getObject(cacheKey: String): DomainModel {
        return try {
            paperBook.read(cacheKey)
        } catch (e : Exception) {
            e.printStackTrace()
            throw ObjectIsNotInCacheException("Não existe nenhum objeto com a chave [$cacheKey] em cache.")
        }
    }

    override fun getList(cacheKey: String): List<DomainModel> {
        TODO("Not yet implemented")
    }

    override fun putObject(model: DomainModel, cacheLimitDate: DateTime?): Boolean {
        model.cacheLimitDate = cacheLimitDate?.toDate()

        return try {
            paperBook.write(model.baseCacheKey, model)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override fun putList(cacheKey: String, modelList: List<DomainModel>, cacheLimitDate: DateTime?): Boolean {
        TODO("Not yet implemented")
    }

    override fun delete(cacheKey: String): Boolean {
        return try {
            paperBook.delete(cacheKey)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}