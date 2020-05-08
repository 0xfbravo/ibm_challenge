package com.ibm.challenge.domain.interactos.cache

import com.ibm.challenge.core.model.DomainModel
import com.ibm.challenge.domain.core.CacheHelper
import com.ibm.challenge.domain.exceptions.InvalidCacheHelperException
import com.ibm.challenge.domain.exceptions.InvalidCacheKeyException
import com.ibm.challenge.domain.exceptions.ObjectIsNotInCacheException
import com.ibm.challenge.domain.repository.local.LocalRepository
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test

class GetCacheObjectTest {

    private lateinit var getCacheObject: GetCacheObject

    private val domainModel = mock<DomainModel> {  }

    val successlocalRepository = mock<LocalRepository> {
        on { getObject(CacheHelper.default, DomainModel::class.java) } doReturn domainModel
    }

    val errorlocalRepository = mock<LocalRepository> {
        on { getObject(CacheHelper.default, DomainModel::class.java) } doThrow ObjectIsNotInCacheException("Não existe nenhum objeto com a chave [${CacheHelper.default.key}] em cache.")
    }

    @Test
    fun testGetCacheObjectWhenCacheKeyIsRight_ShouldReturnSuccess() {
        getCacheObject = GetCacheObject(successlocalRepository)
        val result = getCacheObject.withParams(CacheHelper.default, DomainModel::class.java).execute()
        assertTrue(result == domainModel)
    }

    @Test(expected = ObjectIsNotInCacheException::class)
    fun testGetCacheObjectWhenCacheKeyIsWrong_ShouldReturnError() {
        getCacheObject = GetCacheObject(errorlocalRepository)
        getCacheObject.withParams(CacheHelper.default, DomainModel::class.java).execute()
    }

    @Test(expected = InvalidCacheHelperException::class)
    fun testGetCacheObjectWhenCacheHelperIsNull_ShouldReturnError() {
        getCacheObject = GetCacheObject(errorlocalRepository)
        getCacheObject.execute()
    }

    @Test(expected = InvalidCacheKeyException::class)
    fun testGetCacheObjectWhenCacheKeyIsEmpty_ShouldReturnError() {
        getCacheObject = GetCacheObject(errorlocalRepository)
        getCacheObject.withParams(CacheHelper.empty, DomainModel::class.java).execute()
    }

}