package com.ibm.challenge.login.presentation.view

import android.os.Bundle
import com.ibm.app.login.R
import com.ibm.challenge.login.FeatureLoginModule
import com.ibm.challenge.presentation.core.mvp.BaseActivity
import org.koin.core.context.loadKoinModules

class LoginActivity : BaseActivity() {

    private val loadFeatureModule by lazy { loadKoinModules(FeatureLoginModule.get()) }
    private fun inject() = loadFeatureModule

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        inject()
    }

}
