package com.ibm.challenge.statement.domain.interactors

import com.ibm.challenge.domain.interactos.Interactor
import com.ibm.challenge.domain.repository.LocalRepository
import com.ibm.challenge.statement.domain.exceptions.InvalidUserIdException
import com.ibm.challenge.statement.domain.model.StatementResponseDomain
import com.ibm.challenge.statement.domain.repository.StatementRemoteRepository
import io.reactivex.rxjava3.core.Single

class GetStatements(private val localRepository: LocalRepository,
                    private val remoteRepository: StatementRemoteRepository): Interactor<Single<StatementResponseDomain>>() {

    private var userId = Long.MIN_VALUE

    @Throws(InvalidUserIdException::class)
    override fun execute(): Single<StatementResponseDomain> {
        if (userId < 0)
            throw InvalidUserIdException("O id de usuário [$userId] é inválido.")

        return remoteRepository.getStatements(userId)
    }

    fun withParams(userId: Long): GetStatements {
        this.userId = userId
        return this
    }

    companion object {
        const val tag = "get_statements"
    }

}