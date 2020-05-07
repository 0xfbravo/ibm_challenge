package com.ibm.challenge.login.domain.interactors

import com.ibm.challenge.domain.repository.LocalRepository
import com.ibm.challenge.login.domain.exceptions.InvalidLoginException
import com.ibm.challenge.login.domain.model.ErrorDomain
import com.ibm.challenge.login.domain.model.LoginResponseDomain
import com.ibm.challenge.login.domain.model.UserAccountDomain
import com.ibm.challenge.login.domain.repository.LoginRemoteRepository
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test

class PostLoginTest {

    private lateinit var postLogin: PostLogin
    private val successCpf = "15939427707"
    private val successPassword = "Abc1213$"
    private val errorCpf = "731.344.190-84"
    private val errorPassword = "Abc1213$"

    private val successResponseDomain = LoginResponseDomain(
        userAccount = UserAccountDomain(1, "Jose da Silva Teste", "2050", "012314564", 3.3445)
    )

    private val errorResponseDomain = LoginResponseDomain(
        error = ErrorDomain(53, "Usuário ou senha incorreta")
    )

    @Before
    fun setup() {
        val validateCpf = ValidateCpf()
        val validateEmail = ValidateEmail()
        val validatePassword = ValidatePassword()

        /* Setup LocalRepository */
        val localRepository = mock<LocalRepository> {}

        /* Setup RemoteRepository */
        val remoteRepository = mock<LoginRemoteRepository> {
            on { postLogin(successCpf, successPassword) } doReturn Single.just(successResponseDomain)
            on { postLogin(errorCpf, errorPassword) } doReturn Single.just(errorResponseDomain)
        }

        postLogin = PostLogin(localRepository, remoteRepository, validateCpf, validateEmail, validatePassword)
    }

    @Test
    fun testPostLoginWhenIsRight_ShouldReturnSuccess() {
        val result = postLogin.withParams(successCpf, successPassword).execute()
        val testObserver = result.test()
        testObserver.assertNoErrors()
        testObserver.assertComplete()
        testObserver.assertValue { it.error == null && it.userAccount != null && it.userAccount!!.userID == 1L }
    }

    @Test
    fun testPostLoginWhenIsWrong_ShouldReturnSuccess() {
        val result = postLogin.withParams(errorCpf, errorPassword).execute()
        val testObserver = result.test()
        testObserver.assertNoErrors()
        testObserver.assertComplete()
        testObserver.assertValue { it.error != null && it.userAccount == null && it.error!!.code == 53L }
    }

    @Test(expected = InvalidLoginException::class)
    fun testPostLoginWhenUsernameIsEmpty_ShouldReturnError() {
        postLogin.withParams("", "acb123").execute()
    }

    @Test(expected = InvalidLoginException::class)
    fun testPostLoginWhenUsernameIsInvalidCpf_ShouldReturnError() {
        postLogin.withParams("123", "acb123").execute()
    }

    @Test(expected = InvalidLoginException::class)
    fun testPostLoginWhenUsernameIsInvalidEmail_ShouldReturnError() {
        postLogin.withParams("123@c.", "acb123").execute()
    }

    @Test(expected = InvalidLoginException::class)
    fun testPostLoginWhenPasswordIsEmpty_ShouldReturnError() {
        postLogin.withParams("15939427707", "").execute()
    }

    @Test(expected = InvalidLoginException::class)
    fun testPostLoginWhenPasswordIsInvalid_ShouldReturnError() {
        postLogin.withParams("15939427707", "abc123").execute()
    }

}