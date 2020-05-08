package com.ibm.challenge.domain.interactos.cache

import com.ibm.challenge.core.model.DomainModel
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
    private val rightCacheKey = "abc123"
    private val wrongCacheKey = "error123"

    private val domainModel = mock<DomainModel> {
        on { baseCacheKey } doReturn rightCacheKey
    }

    @Before
    fun setup() {
        /* Setup LocalRepository */
        val localRepository = mock<LocalRepository> {
            on { getObject(rightCacheKey) } doReturn domainModel
            on { getObject(wrongCacheKey) } doThrow ObjectIsNotInCacheException("Não existe nenhum objeto com a chave [$wrongCacheKey] em cache.")
        }

        getCacheObject = GetCacheObject(localRepository)
    }

    @Test
    fun testGetCacheObjectWhenCacheKeyIsRight_ShouldReturnSuccess() {
        val result = getCacheObject.withParams(rightCacheKey).execute()
        assertTrue(result.baseCacheKey == rightCacheKey)
    }

    @Test(expected = ObjectIsNotInCacheException::class)
    fun testGetCacheObjectWhenCacheKeyIsWrong_ShouldReturnError() {
        getCacheObject.withParams(wrongCacheKey).execute()
    }

    @Test(expected = InvalidCacheKeyException::class)
    fun testGetCacheObjectWhenCacheKeyIsEmpty_ShouldReturnError() {
        getCacheObject.execute()
    }

}