package com.ibm.challenge.login.presentation.view

import android.os.Bundle
import com.ibm.app.login.R
import com.ibm.challenge.presentation.core.mvp.BaseActivity

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

}
