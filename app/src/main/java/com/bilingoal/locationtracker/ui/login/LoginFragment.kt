package com.bilingoal.locationtracker.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bilingoal.locationtracker.R
import com.bilingoal.locationtracker.ui.base.BaseFragment
import com.bilingoal.locationtracker.dto.UserAccount
import com.bilingoal.locationtracker.models.validators.*
import com.bilingoal.locationtracker.utils.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

class LoginFragment : BaseFragment<LoginState, LoginViewModel>() {
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    override lateinit var viewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]
        setupTextWatchers()
        setupListeners()
    }

    override fun onStateChange(state: LoginState) {
        when(state) {
            is LoginState.LoadingState -> renderLoadingState()
            is LoginState.FinishState -> renderFinishState(state.userAccount)
            is LoginState.ErrorState -> renderErrorState(state.error)
            is LoginState.InvalidInputFormatState -> renderInvalidInputFormatState(state.errors)
        }
    }

    private fun setupListeners() {
        signInBtn.setOnClickListener { viewModel.authenticate(emailView.value, passwordView.value) }
        signUpBtn.setOnClickListener {
            clearInputs()
            viewModel.registerUser()
        }
    }

    private fun setupTextWatchers() {
        emailView.editText?.addTextChangedListener(textWatcher)
        passwordView.editText?.addTextChangedListener(textWatcher)
    }

    private fun renderFinishState(userAccount: UserAccount?) {
        clearInputs()
        viewModel.goToMainFragment(userAccount)
    }

    private fun renderErrorState(error: Throwable) {
        loginFormView.visibility = View.VISIBLE
        loginProgressBar.visibility = View.GONE
        error.message?.let { Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG).show() }
    }

    private fun renderLoadingState() {
        loginFormView.visibility = View.GONE
        loginProgressBar.visibility = View.VISIBLE
    }

    private fun renderInvalidInputFormatState(errors: List<Int>) {
        errors.forEach {
            when(it) {
                INVALID_EMAIL -> emailView.error = getString(R.string.email_helper_text)
                INVALID_PASSWORD -> passwordView.error = getString(R.string.password_helper_text)
            }
        }
    }

    private fun clearInputs() {
        emailView.error = ""
        passwordView.error = ""
    }

    private val textWatcher = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            clearInputs()
        }
        override fun afterTextChanged(s: Editable?) {}
    }
}