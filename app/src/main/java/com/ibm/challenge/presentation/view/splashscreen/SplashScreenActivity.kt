package com.ibm.challenge.presentation.view.splashscreen

import android.os.Bundle
import com.ibm.challenge.BuildConfig
import com.ibm.challenge.R
import com.ibm.challenge.core.mvp.BaseActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class SplashScreenActivity : BaseActivity(), SplashScreenView {

    private val presenter: SplashScreenPresenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        presenter.attachView(this)
        setupUI()
        navigateToLogin()
    }

    private fun setupUI() {
        setAppVersion()
    }

    private fun setAppVersion() {
        appVersion.text = BuildConfig.VERSION_NAME
    }

    private fun navigateToLogin() {
        presenter.navigateToLogin()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

}
