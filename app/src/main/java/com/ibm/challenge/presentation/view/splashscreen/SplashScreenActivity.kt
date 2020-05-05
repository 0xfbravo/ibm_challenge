package com.ibm.challenge.presentation.view.splashscreen

import android.os.Bundle
import com.ibm.challenge.R
import com.ibm.challenge.presentation.core.mvp.BaseActivity

class SplashScreenActivity : BaseActivity() {

    private lateinit var presenter: SplashScreenPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
    }
}
