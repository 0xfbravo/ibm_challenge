package com.ibm.challenge.core.mvp

interface BaseView {

    fun finish()
    fun showLoading()
    fun hideLoading()
    fun enableTouch()
    fun disableTouch()
    fun hideKeyboard()

}