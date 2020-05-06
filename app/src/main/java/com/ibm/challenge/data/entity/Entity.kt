package com.ibm.challenge.data.entity

import com.ibm.challenge.domain.model.DomainModel

abstract class Entity<T: DomainModel> {

    abstract fun asDomainModel(): T

}