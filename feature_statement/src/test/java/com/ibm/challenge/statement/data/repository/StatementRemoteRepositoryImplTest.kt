package com.ibm.challenge.statement.data.repository

import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class StatementRemoteRepositoryImplTest {

    private val serverPort = 1234
    private val mockWebServer = MockWebServer()
    private lateinit var statementRepositoryImpl: StatementRemoteRepositoryImpl

    private fun getJson(path : String): String {
        return this.javaClass.classLoader!!.getResourceAsStream(path).bufferedReader().use { it.readText() }
    }

    @Before
    fun setup() {
        /* Rx Setup */
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        /* Server setup */
        mockWebServer.start(serverPort)
        statementRepositoryImpl = StatementRemoteRepositoryImpl(mockWebServer.url("/").toString())
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testGetStatementWhenIsRight_ShouldReturnSuccess() {
        /* Server response */
        val response = MockResponse()
            .setResponseCode(200)
            .setBody(getJson("statement/statement_success.json"))

        mockWebServer.enqueue(response)

        /* Init test */
        val testObserver = statementRepositoryImpl.getStatements(1).test()

        val result = testObserver.values().first()
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.dispose()

        assertTrue(result.error != null)
        assertTrue(result.error!!.code == null)
        assertTrue(result.error!!.message == null)

        assertTrue(result.statementList != null)
        assertTrue(result.statementList!!.isNotEmpty())
    }

    @Test
    fun testGetStatementWhenIsWrong_ShouldReturnSuccess() {
        /* Server response */
        val response = MockResponse()
            .setResponseCode(200)
            .setBody(getJson("statement/statement_error.json"))

        mockWebServer.enqueue(response)

        /* Init test */
        val testObserver = statementRepositoryImpl.getStatements(1).test()

        val result = testObserver.values().first()
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.dispose()

        assertTrue(result.error != null)
        assertTrue(result.error!!.code == 123L)
        assertTrue(result.error!!.message == "O id de usuário é inválido")

        assertTrue(result.statementList != null)
        assertTrue(result.statementList!!.isEmpty())
    }

}