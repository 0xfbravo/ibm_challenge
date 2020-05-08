package com.ibm.challenge.data.repository.local

import com.ibm.challenge.core.model.DomainModel
import com.ibm.challenge.domain.core.CacheHelper
import com.ibm.challenge.domain.exceptions.InvalidCacheKeyException
import com.ibm.challenge.domain.exceptions.ObjectIsNotInCacheException
import com.ibm.challenge.domain.repository.LocalRepository
import io.paperdb.Book
import org.joda.time.DateTime

class LocalRepositoryImpl(private val paperBook: Book): LocalRepository {

    override fun getObject(cacheHelper: CacheHelper): DomainModel {
        if (cacheHelper.key.isEmpty())
            throw InvalidCacheKeyException("A chave de cache não pode ser vazia.")

        val cachedObject: DomainModel = paperBook.read<DomainModel>(cacheHelper.key, null)
            ?: throw ObjectIsNotInCacheException("Não existe nenhum objeto com a chave [$cacheHelper] em cache.")

        if (!cacheObjectStillsValid(cacheHelper, cachedObject)) {
            delete(cacheHelper)
            throw ObjectIsNotInCacheException("O tempo de cache máximo do objecto com a chave [$cacheHelper] expirou.")
        }

        return cachedObject
    }

    override fun getList(cacheHelper: CacheHelper): List<DomainModel> {
        if (cacheHelper.key.isEmpty())
            throw InvalidCacheKeyException("A chave de cache não pode ser vazia.")

        val cachedObjectList = paperBook.read<List<DomainModel>>(cacheHelper.key, null)
            ?: throw ObjectIsNotInCacheException("Não existe nenhuma lista de objetos com a chave [$cacheHelper] em cache.")

        if (!cacheObjectStillsValid(cacheHelper, cachedObjectList.first())) {
            delete(cacheHelper)
            throw ObjectIsNotInCacheException("O tempo de cache máximo da lista de objectos com a chave [$cacheHelper] expirou.")
        }

        return cachedObjectList
    }

    override fun putObject(cacheHelper: CacheHelper, model: DomainModel): Boolean {
        if (cacheHelper.key.isEmpty())
            throw InvalidCacheKeyException("A chave de cache não pode ser vazia.")

        model.cachedAt = DateTime.now()
        return try {
            paperBook.write(cacheHelper.key, model)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override fun putList(cacheHelper: CacheHelper, modelList: List<DomainModel>): Boolean {
        if (cacheHelper.key.isEmpty())
            throw InvalidCacheKeyException("A chave de cache não pode ser vazia.")

        val updatedList = modelList.map { it.cachedAt = DateTime.now() }.toList()
        return try {
            paperBook.write(cacheHelper.key, updatedList)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override fun delete(cacheHelper: CacheHelper): Boolean {
        if (cacheHelper.key.isEmpty())
            throw InvalidCacheKeyException("A chave de cache não pode ser vazia.")

        return try {
            paperBook.delete(cacheHelper.key)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun cacheObjectStillsValid(cacheHelper: CacheHelper, cacheObject: DomainModel): Boolean {
        if (cacheHelper.cacheDurationMillis == null)
            return  true

        val now = DateTime.now()
        val cacheLimit = DateTime.now().plus(cacheHelper.cacheDurationMillis)
        return now.isAfter(cacheLimit)
    }

}