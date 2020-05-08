package com.ibm.challenge.statement.presentation.view

import com.ibm.challenge.core.mvp.BaseView
import com.ibm.challenge.presentation.model.statement.StatementModel

interface StatementView: BaseView {

    fun showStatementError(error: String? = null)
    fun updateStatementList(statementList: List<StatementModel>)

}
