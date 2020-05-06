package com.ibm.challenge.login.data.repository

import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.ResponseBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.File
import java.util.concurrent.TimeUnit

class LoginRepositoryImplTest {

    private val serverPort = 1234
    private val mockWebServer = MockWebServer()
    private lateinit var loginRepositoryImpl: LoginRepositoryImpl

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
        loginRepositoryImpl = LoginRepositoryImpl(mockWebServer.url("/").toString())
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testPostLoginWhenIsRight_ShouldReturnSuccess() {
        /* Server response */
        val response = MockResponse()
            .setResponseCode(200)
            .setBody(getJson("login/login_success.json"))

        mockWebServer.enqueue(response)

        /* Init test */
        val testObserver = loginRepositoryImpl.postLogin("abc123", "123abc").test()

        val result = testObserver.values().first()
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.dispose()

        assertTrue(result.error != null)
        assertTrue(result.error!!.code == null)
        assertTrue(result.error!!.message == null)

        assertTrue(result.userAccount != null)
        assertTrue(result.userAccount!!.userID == 1L)
        assertTrue(result.userAccount!!.name == "Jose da Silva Teste")
        assertTrue(result.userAccount!!.bankAccount == "2050")
        assertTrue(result.userAccount!!.agency == "012314564")
        assertTrue(result.userAccount!!.balance == 3.3445)
    }

    @Test
    fun testPostLoginWhenIsWrong_ShouldReturnSuccess() {
        /* Server response */
        val response = MockResponse()
            .setResponseCode(200)
            .setBody(getJson("login/login_error.json"))

        mockWebServer.enqueue(response)

        /* Init test */
        val testObserver = loginRepositoryImpl.postLogin("abc123", "123abc").test()

        val result = testObserver.values().first()
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.dispose()

        assertTrue(result.error != null)
        assertTrue(result.error!!.code == 53L)
        assertTrue(result.error!!.message == "Usuário ou senha incorreta")

        assertTrue(result.userAccount != null)
        assertTrue(result.userAccount!!.userID == null)
        assertTrue(result.userAccount!!.name == null)
        assertTrue(result.userAccount!!.bankAccount == null)
        assertTrue(result.userAccount!!.agency == null)
        assertTrue(result.userAccount!!.balance == null)
    }

}