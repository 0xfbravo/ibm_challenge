package com.ibm.challenge.domain.interactos

abstract class Interactor<T> {

    abstract fun execute(): T

}