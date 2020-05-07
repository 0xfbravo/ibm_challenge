package com.ibm.challenge.presentation.core.mvp

interface BaseView {

    fun showLoading()
    fun hideLoading()
    fun enableTouch()
    fun disableTouch()
    fun hideKeyboard()

}