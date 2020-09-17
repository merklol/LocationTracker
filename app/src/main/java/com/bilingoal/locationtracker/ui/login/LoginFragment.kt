package com.bilingoal.locationtracker.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bilingoal.locationtracker.R
import com.bilingoal.locationtracker.dto.UserAccount
import com.bilingoal.locationtracker.utils.*
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

class LoginFragment : DaggerFragment(), LoginContract.View {

    @Inject
    lateinit var presenter: LoginPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter.subscribe(this)
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signInBtn.setOnClickListener {
            presenter.authenticate(emailView.value, passwordView.value)
        }
        signUpBtn.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegistrationFragment()
            findNavController().navigate(action)
        }
    }

    override fun render(state: LoginState) {
        when(state) {
            is LoginState.LoadingState -> renderLoadingState()
            is LoginState.FinishState -> renderFinishState(state.userAccount)
            is LoginState.ErrorState -> renderErrorState(state.error)
            is LoginState.InvalidInputFormatState -> renderInvalidInputFormatState(state.errorType)
        }
    }

    override fun onStop() {
        super.onStop()
        presenter.unsubscribe()
    }

    private fun renderFinishState(userAccount: UserAccount?) {
        clearInputs()
        val action = LoginFragmentDirections.actionLoginFragmentToMainFragment(userAccount)
        findNavController().navigate(action)
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
        emailView.error = ""
        passwordView.error = ""
    }

    private fun renderInvalidInputFormatState(errorType: Int) {
        when(errorType) {
            INVALID_EMAIL -> {
                emailView.error = "Invalid Email address"
                passwordView.error = ""
            }
            INVALID_PASSWORD -> {
                passwordView.error = "Use at least one numeral and eight characters"
                emailView.error = ""
            }

            INVALID_PASSWORD_EMAIL -> {
                emailView.error = "Invalid Email address"
                passwordView.error = "Use at least one numeral and eight characters"
            }
        }
    }
}