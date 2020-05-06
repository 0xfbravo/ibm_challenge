package com.ibm.challenge.presentation.view.splashscreen

import com.ibm.challenge.presentation.core.Navigator
import com.ibm.challenge.presentation.core.mvp.BasePresenter

class SplashScreenPresenter(private val navigator: Navigator): BasePresenter<SplashScreenView>() {

    fun navigateToLogin() {
        navigator.navigateToLogin()
    }

}
