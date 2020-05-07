package com.ibm.challenge.presentation.core

import android.content.Intent
import com.ibm.challenge.BuildConfig
import com.ibm.challenge.presentation.core.mvp.BaseActivity

class Navigator(private val activity: BaseActivity) {

    enum class AppFeature(val className: String) {
        Login("com.ibm.challenge.app.login.presentation.view.LoginActivity")
    }

    fun navigateToLogin() {
        val intent = Intent()
        intent.setClassName(BuildConfig.APPLICATION_ID, AppFeature.Login.className)
        activity.startActivity(intent)
        activity.finish()
    }

}