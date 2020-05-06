package com.ibm.challenge.presentation.core.mvp

abstract class BasePresenter<T: BaseView> {

    private var view: T?  = null

    fun attachView(view: T) {
        this.view = view
    }

    fun unattachView() {
        this.view = null
    }

}