package com.ibm.challenge.app.login.domain.interactors

import com.ibm.challenge.domain.interactos.Interactor
import com.ibm.challenge.domain.repository.LocalRepository
import com.ibm.challenge.app.login.domain.exceptions.InvalidLoginException
import com.ibm.challenge.app.login.domain.model.LoginResponseDomain
import com.ibm.challenge.app.login.domain.repository.LoginRemoteRepository
import io.reactivex.rxjava3.core.Single

class PostLogin(private val localRepository: LocalRepository,
                private val remoteRepository: LoginRemoteRepository,
                private val validateCpf: ValidateCpf,
                private val validateEmail: ValidateEmail,
                private val validatePassword: ValidatePassword): Interactor<Single<LoginResponseDomain>>() {

    private var user = ""
    private var password = ""

    override fun execute(): Single<LoginResponseDomain> {
        val cpfValidation = validateCpf.withParams(user).execute()
        val emailValidation = validateEmail.withParams(user).execute()

        if (!cpfValidation && !emailValidation)
            throw InvalidLoginException("O usuário [$user] é inválido")

        val passwordValidation = validatePassword.withParams(password).execute()

        if (!passwordValidation)
            throw InvalidLoginException("A senha [$password] é inválida")

        return remoteRepository.postLogin(user, password)
    }

    fun withParams(user: String, password: String): PostLogin {
        this.user = user.trim()
        this.password = password.trim()
        return this
    }

    companion object {
        const val tag = "post_login"
    }

}