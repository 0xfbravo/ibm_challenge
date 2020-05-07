package com.ibm.challenge.core.mvp

abstract class BasePresenter<T: BaseView> {

    var view: T?  = null

    fun attachView(view: T) {
        this.view = view
    }

    fun unattachView() {
        this.view = null
    }

}