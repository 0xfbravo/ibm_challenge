package com.ibm.challenge.login.presentation.view

import com.ibm.challenge.core.mvp.BaseView

interface LoginView: BaseView {

    fun maskUserCpf()
    fun showLoginError(error: String? = null)
    fun changeLoginButtonState(enabled: Boolean)
    fun showWelcomeBack()
    fun hideWelcomeBack()

}
