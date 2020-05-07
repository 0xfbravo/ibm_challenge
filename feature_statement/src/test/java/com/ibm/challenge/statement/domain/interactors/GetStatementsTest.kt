package com.ibm.challenge.statement.domain.interactors

import com.ibm.challenge.domain.repository.LocalRepository
import com.ibm.challenge.statement.domain.exceptions.InvalidUserIdException
import com.ibm.challenge.statement.domain.model.StatementDomain
import com.ibm.challenge.statement.domain.model.StatementErrorDomain
import com.ibm.challenge.statement.domain.model.StatementResponseDomain
import com.ibm.challenge.statement.domain.repository.StatementRemoteRepository
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test

class GetStatementsTest {

    private lateinit var getStatements: GetStatements
    private val successUserId = 1L
    private val errorUserId = 42L

    private val successResponseDomain = StatementResponseDomain(
        statementList = arrayListOf(
            StatementDomain("Pagamento", "Conta de luz", "2018-08-15", -50.0),
            StatementDomain("TED Recebida", "Joao Alfredo", "2018-07-25", 745.03),
            StatementDomain("DOC Recebida", "Victor Silva", "2018-06-23", 399.9)
        )
    )

    private val errorResponseDomain = StatementResponseDomain(
        error = StatementErrorDomain(53, "ID de usuário inválido")
    )

    @Before
    fun setup() {
        /* Setup LocalRepository */
        val localRepository = mock<LocalRepository> {}

        /* Setup RemoteRepository */
        val remoteRepository = mock<StatementRemoteRepository> {
            on { getStatements(successUserId) } doReturn Single.just(successResponseDomain)
            on { getStatements(errorUserId) } doReturn Single.just(errorResponseDomain)
        }

        getStatements = GetStatements(localRepository, remoteRepository)
    }

    @Test
    fun testGetStatementsWhenIsRight_ShouldReturnSuccess() {
        val result = getStatements.withParams(successUserId).execute()
        val testObserver = result.test()
        testObserver.assertNoErrors()
        testObserver.assertComplete()
        testObserver.assertValue { it.error == null && it.statementList != null && it.statementList!!.size == 3 }
    }

    @Test
    fun testGetStatementsWhenIsWrong_ShouldReturnSuccess() {
        val result = getStatements.withParams(errorUserId).execute()
        val testObserver = result.test()
        testObserver.assertNoErrors()
        testObserver.assertComplete()
        testObserver.assertValue { it.error != null && it.statementList == null && it.error!!.code == 53L }
    }

    @Test(expected = InvalidUserIdException::class)
    fun testGetStatementsWhenUserIsEmpty_ShouldReturnError() {
        getStatements.withParams(Long.MIN_VALUE).execute()
    }

}