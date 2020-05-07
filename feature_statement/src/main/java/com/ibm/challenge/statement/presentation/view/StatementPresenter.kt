package com.ibm.challenge.statement.presentation.view

import com.ibm.challenge.statement.domain.exceptions.InvalidUserIdException
import com.ibm.challenge.statement.domain.interactors.GetStatements
import com.ibm.challenge.statement.domain.model.StatementResponseDomain
import com.ibm.challenge.core.Navigator
import com.ibm.challenge.core.mvp.BasePresenter
import com.ibm.challenge.presentation.model.login.UserAccountModel

class StatementPresenter(private val navigator: Navigator,
                         private val getStatements: GetStatements): BasePresenter<StatementView>() {

    lateinit var currentUser: UserAccountModel

    fun getStatements() {
        view?.showLoading()
        try {
            getStatements
                .withParams(currentUser.userID!!)
                .execute()
                .subscribe({ handleGetStatementsSuccess(it) }, { handleGetStatementsError(it) })
        }
        catch (e : InvalidUserIdException) {
            e.printStackTrace()
            view?.hideLoading()
            view?.showStatementError()
        }
    }

    private fun handleGetStatementsSuccess(response: StatementResponseDomain) {
        TODO("Not implemented yet")
    }

    private fun handleGetStatementsError(error: Throwable) {
        TODO("Not implemented yet")
    }

}