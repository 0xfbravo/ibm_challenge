package com.ibm.challenge.domain.interactos.cache

import com.ibm.challenge.core.model.DomainModel
import com.ibm.challenge.domain.exceptions.InvalidDomainModelException
import com.ibm.challenge.domain.repository.local.LocalRepository
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test

class PutCacheObjectTest {

    private lateinit var putCacheObject: PutCacheObject
    private val successCacheKey = "abc123"
    private val errorCacheKey = "error123"

    private val successDomainModel = mock<DomainModel> {
        on { baseCacheKey } doReturn successCacheKey
    }

    private val errorDomainModel = mock<DomainModel> {
        on { baseCacheKey } doReturn errorCacheKey
    }

    @Before
    fun setup() {
        /* Setup LocalRepository */
        val localRepository = mock<LocalRepository> {
            on { putObject(successDomainModel) } doReturn true
            on { putObject(errorDomainModel) } doReturn false
        }

        putCacheObject = PutCacheObject(localRepository)
    }

    @Test
    fun testPutCacheObjectWhenCacheObjectIsRight_ShouldReturnSuccess() {
        val result = putCacheObject.withParams(successDomainModel).execute()
        assertTrue(result)
    }

    @Test
    fun testPutCacheObjectWhenCacheObjectIsWrong_ShouldReturnError() {
        val result = putCacheObject.withParams(errorDomainModel).execute()
        assertFalse(result)
    }

    @Test(expected = InvalidDomainModelException::class)
    fun testPutCacheObjectWhenCacheObjectIsNull_ShouldReturnError() {
        val result = putCacheObject.execute()
        assertFalse(result)
    }

}