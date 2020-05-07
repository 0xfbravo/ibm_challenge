package com.ibm.challenge.login.presentation.view

import com.ibm.challenge.presentation.core.mvp.BaseView

interface LoginView: BaseView {

    fun maskUserCpf()
    fun showLoginError(error: String? = null)
    fun changeLoginButtonState(enabled: Boolean)

}
