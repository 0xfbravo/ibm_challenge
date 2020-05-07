package com.ibm.challenge.statement.presentation.view

import com.ibm.challenge.core.Navigator
import com.ibm.challenge.core.mvp.BasePresenter
import com.ibm.challenge.presentation.model.login.UserAccountModel
import com.ibm.challenge.statement.core.PresentationModelMapper
import com.ibm.challenge.statement.domain.exceptions.InvalidUserIdException
import com.ibm.challenge.statement.domain.interactors.GetStatements
import com.ibm.challenge.statement.domain.model.StatementResponseDomain

class StatementPresenter(private val navigator: Navigator,
                         private val getStatements: GetStatements): BasePresenter<StatementView>() {

    var currentUser: UserAccountModel? = null
    set(value) {
        field = value
        getStatements()
    }

    private fun getStatements() {
        view?.showLoading()
        try {
            getStatements
                .withParams(currentUser!!.userID!!)
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
        val responseModel = PresentationModelMapper.mapStatementResponse(response)
        if (responseModel.error != null && !responseModel.error?.message.isNullOrEmpty()) {
            view?.hideLoading()
            view?.showStatementError(responseModel.error?.message)
            return
        }

        if (responseModel.statementList.isNullOrEmpty()) {
            view?.hideLoading()
            view?.showStatementError()
            return
        }

        view?.hideLoading()
        view?.updateStatementList(responseModel.statementList!!)
    }

    private fun handleGetStatementsError(error: Throwable) {
        error.printStackTrace()
        view?.hideLoading()
        view?.showStatementError()
    }

}