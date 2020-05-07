package com.ibm.challenge.login.presentation.view

import com.ibm.challenge.login.domain.exceptions.InvalidLoginException
import com.ibm.challenge.login.domain.interactors.PostLogin
import com.ibm.challenge.login.domain.interactors.ValidateCpf
import com.ibm.challenge.login.domain.interactors.ValidateEmail
import com.ibm.challenge.login.domain.interactors.ValidatePassword
import com.ibm.challenge.login.domain.model.LoginResponseDomain
import com.ibm.challenge.login.presentation.model.LoginResponseModel
import com.ibm.challenge.presentation.core.Navigator
import com.ibm.challenge.presentation.core.mvp.BasePresenter

class LoginPresenter(private val navigator: Navigator,
                     private val validateCpf: ValidateCpf,
                     private val validateEmail: ValidateEmail,
                     private val validatePassword: ValidatePassword,
                     private val postLogin: PostLogin): BasePresenter<LoginView>() {

    var currentUser: String = ""
    set(value) {
        if (value == field)
            return

        field = value

        if (validateCpf(field))
            view?.maskUserCpf()

        view?.changeLoginButtonState(isLoginButtonEnabled())
    }

    var currentPassword: String = ""
    set(value) {
        field = value
        view?.changeLoginButtonState(isLoginButtonEnabled())
    }

    fun handleLoginButtonClick() {
        view?.showLoading()
        try {
            postLogin
                .withParams(currentUser, currentPassword)
                .execute()
                .subscribe({ handlePostLoginSuccess(it) }, { handlePostLoginError(it) })
        }
        catch (e : InvalidLoginException) {
            e.printStackTrace()
            view?.hideLoading()
            view?.showLoginError()
        }
    }

    private fun handlePostLoginError(error: Throwable) {
        error.printStackTrace()
        view?.hideLoading()
        view?.showLoginError()
    }

    private fun handlePostLoginSuccess(response: LoginResponseDomain) {
        val responseModel = LoginResponseModel.fromDomainModel(response)
        if (responseModel.error != null && !responseModel.error.message.isNullOrEmpty()) {
            view?.hideLoading()
            view?.showLoginError(responseModel.error.message)
            return
        }
    }

    private fun isLoginButtonEnabled(): Boolean {
        val isUserValid = validateCpf(currentUser) || validateEmail(currentUser)
        val isPasswordValid = validatePassword(currentPassword)
        return isUserValid && isPasswordValid
    }

    private fun validateCpf(text: String): Boolean {
        return validateCpf.withParams(text).execute()
    }

    private fun validateEmail(text: String): Boolean {
        return validateEmail.withParams(text).execute()
    }

    private fun validatePassword(text: String): Boolean {
        return validatePassword.withParams(text).execute()
    }

    private fun navigateToStatements() {

    }

}
