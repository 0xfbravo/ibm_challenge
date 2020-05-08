package com.ibm.challenge.domain.interactos.cache

import com.ibm.challenge.core.model.DomainModel
import com.ibm.challenge.domain.core.CacheHelper
import com.ibm.challenge.domain.exceptions.InvalidCacheHelperException
import com.ibm.challenge.domain.exceptions.InvalidCacheKeyException
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

    private val successDomainModel = mock<DomainModel> { }
    private val errorDomainModel = mock<DomainModel> { }

    @Before
    fun setup() {
        /* Setup LocalRepository */
        val localRepository = mock<LocalRepository> {
            on { putObject(CacheHelper.default, successDomainModel) } doReturn true
            on { putObject(CacheHelper.default, errorDomainModel) } doReturn false
        }

        putCacheObject = PutCacheObject(localRepository)
    }

    @Test
    fun testPutCacheObjectWhenCacheObjectIsRight_ShouldReturnSuccess() {
        val result = putCacheObject.withParams(CacheHelper.default, successDomainModel).execute()
        assertTrue(result)
    }

    @Test
    fun testPutCacheObjectWhenCacheObjectIsWrong_ShouldReturnError() {
        val result = putCacheObject.withParams(CacheHelper.default, errorDomainModel).execute()
        assertFalse(result)
    }

    @Test(expected = InvalidCacheHelperException::class)
    fun testPutCacheObjectWhenCacheObjectIsNull_ShouldReturnError() {
        val result = putCacheObject.execute()
        assertFalse(result)
    }

    @Test(expected = InvalidCacheKeyException::class)
    fun testPutCacheObjectWhenCacheKeyIsEmpty_ShouldReturnError() {
        val result = putCacheObject.withParams(CacheHelper.empty, errorDomainModel).execute()
        assertFalse(result)
    }

}