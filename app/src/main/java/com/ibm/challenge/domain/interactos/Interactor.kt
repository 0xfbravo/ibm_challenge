package com.ibm.challenge.domain.interactos

import com.ibm.challenge.domain.repository.LocalRepository
import com.ibm.challenge.domain.repository.RemoteRepository

abstract class Interactor<T>(private val localRepository: LocalRepository? = null,
                             private val remoteRepository: RemoteRepository? = null) {

    abstract fun execute(): T

}