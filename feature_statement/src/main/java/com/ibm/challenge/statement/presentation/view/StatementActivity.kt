package com.ibm.challenge.statement.presentation.view

import android.os.Bundle
import com.ibm.challenge.app.statement.R
import com.ibm.challenge.core.mvp.BaseActivity
import com.ibm.challenge.statement.FeatureStatementModule
import org.koin.android.ext.android.inject
import org.koin.core.context.loadKoinModules
import org.koin.core.parameter.parametersOf

class StatementActivity : BaseActivity() {

    private val loadFeatureModule by lazy { loadKoinModules(FeatureStatementModule.get()) }
    private fun inject() = loadFeatureModule

    private val presenter: StatementPresenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statement)
        setupUI()
    }

    private fun setupUI() {

    }

}
