package com.ibm.challenge.login.domain.interactors

import com.ibm.challenge.domain.interactos.Interactor

class ValidateEmail: Interactor<Boolean>() {

    private var rfc2822EmailRegex = Regex("[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")
    private var email = ""

    override fun execute(): Boolean {
        return rfc2822EmailRegex.matches(email)
    }

    fun withParams(password: String): ValidateEmail {
        this.email = password.trim()
        return this
    }

}