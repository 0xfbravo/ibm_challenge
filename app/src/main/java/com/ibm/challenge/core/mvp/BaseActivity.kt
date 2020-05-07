package com.ibm.challenge.core.mvp

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.ibm.challenge.R

abstract class BaseActivity: Activity(), BaseView {

    private var loadingLayout: View? = null
    private lateinit var rootLayout: ViewGroup

    override fun showLoading() = runOnUiThread {
        disableTouch()
        createLoading()
    }

    override fun hideLoading() = runOnUiThread {
        enableTouch()
        removeLoading()
    }

    private fun createLoading() = runOnUiThread {
        if (loadingLayout == null) {
            rootLayout = findViewById<View>(android.R.id.content) as ViewGroup
            val layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            loadingLayout = layoutInflater.inflate(R.layout.loading, rootLayout, true)
            hideKeyboard()
        }
    }

    private fun removeLoading() = runOnUiThread {
        if (loadingLayout != null) {
            val loading = rootLayout.findViewById<View>(R.id.loader)
            rootLayout.removeView(loading)
            loadingLayout = null
        }
    }

    override fun enableTouch() = runOnUiThread {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    override fun disableTouch() = runOnUiThread {
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    override fun hideKeyboard() = runOnUiThread {
        val view = if (currentFocus != null) currentFocus else View(this)
        val imm = getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager

        if (view != null) {
            imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

}