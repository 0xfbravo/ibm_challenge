package com.ibm.challenge.presentation.core.mvp

import com.ibm.challenge.domain.model.DomainModel

abstract class PresentationModel<T: DomainModel> {

    abstract fun asDomainModel(): T

}