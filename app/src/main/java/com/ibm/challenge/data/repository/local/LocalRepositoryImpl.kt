package com.ibm.challenge.data.repository.local

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.ibm.challenge.core.model.DomainModel
import com.ibm.challenge.data.repository.security.CipherWrapper
import com.ibm.challenge.domain.core.CacheHelper
import com.ibm.challenge.domain.exceptions.InvalidCacheKeyException
import com.ibm.challenge.domain.exceptions.ObjectIsNotInCacheException
import com.ibm.challenge.domain.repository.local.LocalRepository
import com.ibm.challenge.domain.repository.security.KeystoreRepository
import io.paperdb.Book
import org.joda.time.DateTime

class LocalRepositoryImpl(private val gson: Gson,
                          private val paperBook: Book,
                          private val keystoreRepository: KeystoreRepository,
                          private val cipherWrapper: CipherWrapper): LocalRepository {

    private val keyStoreAlias = "localRepository"
    private val secretKey = keystoreRepository.generateSymmetricKey(keyStoreAlias, userAuthenticationRequired = false, invalidatedByBiometricEnrollment = true)

    override fun getObject(cacheHelper: CacheHelper, cacheObjectClass: Class<out DomainModel>): DomainModel {
        if (cacheHelper.key.isEmpty())
            throw InvalidCacheKeyException("A chave de cache não pode ser vazia.")

        val cachedEncryptedString = paperBook.read<String>(cacheHelper.key, null)
            ?: throw ObjectIsNotInCacheException("Não existe nenhum objeto com a chave [$cacheHelper] em cache.")

        val decryptedJson = decryptData(cachedEncryptedString)
        val decryptedObject = gson.fromJson(decryptedJson, cacheObjectClass)

        if (!cacheObjectStillsValid(cacheHelper, decryptedObject)) {
            delete(cacheHelper)
            throw ObjectIsNotInCacheException("O tempo de cache máximo do objecto com a chave [$cacheHelper] expirou.")
        }

        return decryptedObject
    }

    override fun getList(cacheHelper: CacheHelper, cacheObjectClass: Class<out DomainModel>): List<DomainModel> {
        if (cacheHelper.key.isEmpty())
            throw InvalidCacheKeyException("A chave de cache não pode ser vazia.")

        val cachedEncryptedString = paperBook.read<String>(cacheHelper.key, null)
            ?: throw ObjectIsNotInCacheException("Não existe nenhuma lista de objetos com a chave [$cacheHelper] em cache.")

        val parser = JsonParser()
        val decryptedJson = decryptData(cachedEncryptedString)
        val jsonElement = parser.parse(decryptedJson)
        val decryptedObjectList = jsonElement.asJsonArray.map { gson.fromJson(it.asJsonObject, cacheObjectClass) }.toList()

        if (!cacheObjectStillsValid(cacheHelper, decryptedObjectList.first())) {
            delete(cacheHelper)
            throw ObjectIsNotInCacheException("O tempo de cache máximo da lista de objectos com a chave [$cacheHelper] expirou.")
        }

        return decryptedObjectList
    }

    override fun putObject(cacheHelper: CacheHelper, model: DomainModel): Boolean {
        if (cacheHelper.key.isEmpty())
            throw InvalidCacheKeyException("A chave de cache não pode ser vazia.")

        model.cachedAt = DateTime.now()
        return try {
            val json = gson.toJson(model)
            val encryptedJson = encryptData(json)
            paperBook.write(cacheHelper.key, encryptedJson)
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
            val json = gson.toJson(updatedList)
            val encryptedJson = encryptData(json)
            paperBook.write(cacheHelper.key, encryptedJson)
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

        if (cacheObject.cachedAt == null)
            return false

        val now = DateTime.now()
        val cacheLimit = cacheObject.cachedAt?.plus(cacheHelper.cacheDurationMillis)
        return now.isAfter(cacheLimit)
    }

    private fun encryptData(data: String): String {
        val dataByteArray = data.toByteArray()
        return cipherWrapper.encryptAES(dataByteArray, secretKey)
    }

    private fun decryptData(data: String): String {
        val decryptedData = cipherWrapper.decryptAES(data, secretKey)
        return String(decryptedData)
    }

}