package com.ibm.challenge.presentation.view.splashscreen

import android.os.Bundle
import com.ibm.challenge.R
import com.ibm.challenge.presentation.core.mvp.BaseActivity
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class SplashScreenActivity : BaseActivity(), SplashScreenView {

    private val presenter: SplashScreenPresenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        presenter.attachView(this)
        presenter.navigateToLogin()
    }
}
