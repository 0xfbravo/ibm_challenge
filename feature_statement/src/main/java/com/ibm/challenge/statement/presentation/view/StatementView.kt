package com.ibm.challenge.statement.presentation.view

import com.ibm.challenge.core.mvp.BaseView

interface StatementView: BaseView {

    fun showStatementError(error: String? = null)

}
