package com.ibm.challenge.statement.presentation.view

import android.os.Bundle
import com.ibm.challenge.app.statement.R
import com.ibm.challenge.core.Formats
import com.ibm.challenge.core.mvp.BaseActivity
import com.ibm.challenge.presentation.model.login.UserAccountModel
import com.ibm.challenge.statement.FeatureStatementModule
import kotlinx.android.synthetic.main.activity_statement.*
import org.koin.android.ext.android.inject
import org.koin.core.context.loadKoinModules
import org.koin.core.parameter.parametersOf

class StatementActivity : BaseActivity(), StatementView  {

    private val loadFeatureModule by lazy { loadKoinModules(FeatureStatementModule.get()) }
    private fun inject() = loadFeatureModule

    private val presenter: StatementPresenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statement)
        presenter.attachView(this)
        presenter.currentUser = intent.getParcelableExtra(UserAccountModel.bundleKey)!!
        setupUI()
    }

    private fun setupUI() {
        name.text = presenter.currentUser.name
        account.text = "${presenter.currentUser.bankAccount} / ${presenter.currentUser.agency}"
        balance.text = Formats.currencyFormatter.format(presenter.currentUser.balance)
    }

    override fun showStatementError(error: String?) {
        TODO("Not yet implemented")
    }
}
