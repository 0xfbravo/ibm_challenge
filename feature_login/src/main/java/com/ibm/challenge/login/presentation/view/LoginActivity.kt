package com.ibm.challenge.login.presentation.view

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import androidx.core.widget.doOnTextChanged
import com.ibm.app.login.R
import com.ibm.challenge.core.Maskify
import com.ibm.challenge.login.FeatureLoginModule
import com.ibm.challenge.presentation.core.mvp.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.ext.android.inject
import org.koin.core.context.loadKoinModules
import org.koin.core.parameter.parametersOf

class LoginActivity : BaseActivity(), LoginView {

    private val loadFeatureModule by lazy { loadKoinModules(FeatureLoginModule.get()) }
    private fun inject() = loadFeatureModule

    private val presenter: LoginPresenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        presenter.attachView(this)
        setupUI()
    }

    private fun setupUI() = runOnUiThread  {
        userEditText.doOnTextChanged { text, _, _, _ ->
            presenter.currentUser = text.toString()
        }

        passwordEditText.doOnTextChanged { text, _, _, _ ->
            presenter.currentPassword = text.toString()
        }

        loginButton.setOnClickListener {
            presenter.handleLoginButtonClick()
        }
    }

    override fun maskUserCpf() = runOnUiThread {
        val formattedCpf = Maskify.with(presenter.currentUser, Maskify.Type.CPF)
        userEditText.setText(formattedCpf)
        userEditText.setSelection(formattedCpf.length)
    }

    override fun showLoginError(error: String?) = runOnUiThread  {
        val errorMessage = error ?: getString(R.string.generic_login_error)

        val builder = AlertDialog.Builder(this)
        builder
            .setTitle(R.string.login_error_title)
            .setMessage(errorMessage)
            .setNeutralButton(R.string.ok, null)
            .show()
    }

    override fun changeLoginButtonState(enabled: Boolean) = runOnUiThread  {
        loginButton.isEnabled = enabled
    }
}
