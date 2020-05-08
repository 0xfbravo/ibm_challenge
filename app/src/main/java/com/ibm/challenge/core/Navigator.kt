package com.ibm.challenge.core

import android.content.Intent
import com.ibm.challenge.BuildConfig
import com.ibm.challenge.core.mvp.BaseActivity
import com.ibm.challenge.presentation.model.login.UserAccountModel

class Navigator(private val activity: BaseActivity) {

    enum class AppFeature(val className: String) {
        Login("com.ibm.challenge.login.presentation.view.LoginActivity"),
        Statement("com.ibm.challenge.statement.presentation.view.StatementActivity")
    }

    fun navigateToLogin() {
        val intent = Intent()
        intent.apply {
            setClassName(BuildConfig.APPLICATION_ID, AppFeature.Login.className)
        }
        activity.startActivity(intent)
    }

    fun navigateToStatement(userAccountModel: UserAccountModel) {
        val intent = Intent()
        intent.apply {
            setClassName(BuildConfig.APPLICATION_ID, AppFeature.Statement.className)
            putExtra(UserAccountModel.bundleKey, userAccountModel)
        }
        activity.startActivity(intent)
    }

}