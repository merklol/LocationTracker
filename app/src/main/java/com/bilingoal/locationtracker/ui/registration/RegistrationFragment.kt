package com.bilingoal.locationtracker.ui.registration

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bilingoal.locationtracker.R
import com.bilingoal.locationtracker.ui.base.BaseFragment
import com.bilingoal.locationtracker.di.utils.DaggerViewModelFactory
import com.bilingoal.locationtracker.dto.UserAccount
import com.bilingoal.locationtracker.dto.UserInput
import com.bilingoal.locationtracker.utils.*
import com.bilingoal.locationtracker.models.validators.CredentialsValidation
import com.bilingoal.locationtracker.models.validators.CredentialsValidator
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_registration.*
import javax.inject.Inject

class RegistrationFragment : BaseFragment<RegistrationState, RegistrationViewModel>() {
    @Inject
    lateinit var factory: DaggerViewModelFactory
    override lateinit var viewModel: RegistrationViewModel
    private val validator = CredentialsValidator()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, factory)[RegistrationViewModel::class.java]

        nameView.editText?.addTextChangedListener(textWatcher)
        emailView.editText?.addTextChangedListener(textWatcher)
        passwordView.editText?.addTextChangedListener(textWatcher)

        signUpBtn.setOnClickListener { viewModel.createUser(nameView.value, emailView.value, passwordView.value) }
    }

    override fun onStateChange(state: RegistrationState) {
        when(state) {
            is RegistrationState.LoadingState -> renderLoadingState()
            is RegistrationState.FinishState -> renderFinishState(state.userAccount)
            is RegistrationState.ErrorState -> renderErrorState(state.error)
        }
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

    private fun clearInputs() {
        nameView.error = ""
        emailView.error = ""
        passwordView.error = ""
    }
    
    private val textWatcher = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            when (s.hashCode()) {
                emailView.valueHashCode -> {
                    val error = validator.execute(s.toString(), CredentialsValidation.EMAIL)
                    showHelperText(emailView, getString(R.string.email_helper_text), error)
                }
                passwordView.valueHashCode -> {
                    val error = validator.execute(s.toString(), CredentialsValidation.PASSWORD)
                    showHelperText(passwordView, getString(R.string.password_helper_text), error)
                }
                nameView.valueHashCode -> {
                    val error = validator.execute(s.toString(), CredentialsValidation.NAME)
                    showHelperText(nameView, getString(R.string.name_helper_text), error)
                }
            }
            enableSignUpBtn()
        }
    }

    private fun showHelperText(layout: TextInputLayout, value: String, res: Int) {
        layout.helperText = if(res == -1) "" else value
    }

    private fun enableSignUpBtn() {
        val res = validator.execute(UserInput(emailView.value, passwordView.value, nameView.value),
            CredentialsValidation.NAME, CredentialsValidation.EMAIL, CredentialsValidation.PASSWORD)
        signUpBtn.isEnabled = res.isEmpty()
    }
}