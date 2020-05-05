package com.ibm.challenge.login.domain.interactors

import com.ibm.challenge.domain.interactos.Interactor

class ValidatePassword: Interactor<Boolean>() {

    private var passwordRegex = Regex("(?=.*[A-Z])(?=.*[@\$!%*#?&])(?=.*\\d)[A-Za-z\\d@\$!%*#?&]{3,}")
    private var password = ""

    override fun execute(): Boolean {
        return passwordRegex.matches(password)
    }

    fun withParams(password: String): ValidatePassword {
        this.password = password
        return this
    }

}