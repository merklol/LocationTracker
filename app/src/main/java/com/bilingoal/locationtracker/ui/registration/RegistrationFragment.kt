package com.bilingoal.locationtracker.ui.registration

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bilingoal.locationtracker.R
import com.bilingoal.locationtracker.di.utils.DaggerViewModelFactory
import com.bilingoal.locationtracker.dto.UserAccount
import com.bilingoal.locationtracker.utils.*
import com.bilingoal.locationtracker.models.validators.VALID_INPUT_FORMAT
import com.bilingoal.locationtracker.ui.base.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_registration.*
import javax.inject.Inject

class RegistrationFragment : BaseFragment<RegistrationState, RegistrationViewModel, Host>(), RegistrationView {
    @Inject
    lateinit var factory: DaggerViewModelFactory
    override val reducer: Reducer by reducers()
    override lateinit var viewModel: RegistrationViewModel

    private val textWatcher = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            when (s.hashCode()) {
                nameView.valueHashCode -> viewModel.validateName(s.toString())
                emailView.valueHashCode -> viewModel.validateEmail(s.toString())
                passwordView.valueHashCode -> viewModel.validatePassword(s.toString())
            }
            viewModel.registrationState()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, factory)[RegistrationViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTextWatchers()
        setupListeners()
    }

    override fun renderFinishState(userAccount: UserAccount?) {
        clearInputs()
        viewModel.navigateToMainScreen(userAccount)
    }

    override fun renderErrorState(error: Throwable) {
        loginFormView.visibility = View.VISIBLE
        loginProgressBar.visibility = View.GONE
        error.message?.let { Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG).show() }
    }

    override fun renderLoadingState() {
        loginFormView.visibility = View.GONE
        loginProgressBar.visibility = View.VISIBLE
    }

    override fun renderRegistrationAvailableState() {
        signUpBtn.isEnabled = true
    }

    override fun renderRegistrationUnavailableState() {
        signUpBtn.isEnabled = false
    }

    override fun renderInvalidNameFormatState(res: Int) {
         nameView.helperText =
             if (res != VALID_INPUT_FORMAT)
                 getString(R.string.name_helper_text)
             else ""
     }

    override fun renderInvalidEmailFormatState(res: Int) {
        emailView.helperText =
            if (res != VALID_INPUT_FORMAT)
                getString(R.string.email_helper_text)
            else ""
    }

    override fun renderInvalidPasswordFormatState(res: Int) {
        passwordView.helperText =
            if (res != VALID_INPUT_FORMAT)
                getString(R.string.password_helper_text)
            else ""
    }

    private fun setupListeners() {
        signUpBtn.setOnClickListener {
            viewModel.createUser(nameView.value, emailView.value, passwordView.value)
        }
    }

    private fun setupTextWatchers() {
        nameView.editText?.addTextChangedListener(textWatcher)
        emailView.editText?.addTextChangedListener(textWatcher)
        passwordView.editText?.addTextChangedListener(textWatcher)
    }

    private fun clearInputs() {
        nameView.error = ""
        emailView.error = ""
        passwordView.error = ""
    }
}