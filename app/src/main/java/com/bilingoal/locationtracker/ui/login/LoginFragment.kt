package com.bilingoal.locationtracker.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bilingoal.locationtracker.R
import com.bilingoal.locationtracker.dto.UserAccount
import com.bilingoal.locationtracker.models.validators.*
import com.bilingoal.locationtracker.ui.base.*
import com.bilingoal.locationtracker.utils.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

class LoginFragment : BaseFragment<LoginState, LoginViewModel, Host>(), LoginView {
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    override val reducer: Reducer by reducers()
    override lateinit var viewModel: LoginViewModel

    private val textWatcher = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            clearInputs()
        }
        override fun afterTextChanged(s: Editable?) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTextWatchers()
        setupListeners()
        viewModel.autoLogin()
        fragmentHost?.test()
    }

    override fun renderFinishState(userAccount: UserAccount?) {
        viewModel.navigateToMainScreen(userAccount)
    }

    override fun renderErrorState(error: Throwable) {
        loginFormView.visibility = View.VISIBLE
        loginProgressBar.visibility = View.GONE
        error.message?.let { Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG).show() }
    }

    override fun renderDefaultState() {
        loginFormView.visibility = View.VISIBLE
        clearInputs()
    }

    override fun renderLoadingState() {
        loginFormView.visibility = View.GONE
        loginProgressBar.visibility = View.VISIBLE
    }

    override fun renderInvalidInputFormatState(errors: List<Int>) {
        errors.forEach {
            when(it) {
                INVALID_EMAIL -> emailView.error = getString(R.string.email_helper_text)
                INVALID_PASSWORD -> passwordView.error = getString(R.string.password_helper_text)
            }
        }
    }

    private fun setupListeners() {
        signInBtn.setOnClickListener { viewModel.authenticate(emailView.value, passwordView.value) }
        signUpBtn.setOnClickListener { viewModel.navigateToRegistration() }
    }

    private fun setupTextWatchers() {
        emailView.editText?.addTextChangedListener(textWatcher)
        passwordView.editText?.addTextChangedListener(textWatcher)
    }

    private fun clearInputs() {
        emailView.error = ""
        passwordView.error = ""
    }
}