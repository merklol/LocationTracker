package com.bilingoal.locationtracker.ui.registration

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
import kotlinx.android.synthetic.main.fragment_registration.*
import javax.inject.Inject

class RegistrationFragment : DaggerFragment(), RegistrationContract.View {
    @Inject
    lateinit var presenter: RegistrationPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter = RegistrationPresenter(RegistrationInteractorImpl())
        presenter.subscribe(this)
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signUpBtn.setOnClickListener {
           presenter.createUser(nameView.value, emailView.value, passwordView.value)
        }
    }

    override fun render(state: RegistrationState) {
        when(state) {
            is RegistrationState.LoadingState -> renderLoadingState()
            is RegistrationState.FinishState -> renderFinishState(state.userAccount)
            is RegistrationState.ErrorState -> renderErrorState(state.error)
            is RegistrationState.InvalidInputFormatState -> renderInvalidInputFormatState(state.errorType)
        }
    }

    override fun onStop() {
        super.onStop()
        presenter.unsubscribe()
    }


    private fun renderFinishState(userAccount: UserAccount?) {
        clearInputs()
        val action = RegistrationFragmentDirections.actionRegistrationFragmentToMainFragment(userAccount)
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
        nameView.error = ""
        emailView.error = ""
        passwordView.error = ""
    }

    private fun renderInvalidInputFormatState(errorType: Int) {
        when(errorType) {
            INVALID_NAME -> {
                nameView.error = "Name must be equal or greater 2 characters"
                emailView.error = ""
                passwordView.error = ""
            }

            INVALID_EMAIL -> {
                emailView.error = "Invalid Email address"
                passwordView.error = ""
                nameView.error = ""
            }
            INVALID_PASSWORD -> {
                passwordView.error = "Use at least one numeral and eight characters"
                emailView.error = ""
                nameView.error = ""
            }

            INVALID_PASSWORD_EMAIL -> {
                emailView.error = "Invalid Email address"
                passwordView.error = "Use at least one numeral and eight characters"
                nameView.error = ""
            }

            INVALID_PASSWORD_NAME -> {
                emailView.error = "Invalid Email address"
                passwordView.error = "Use at least one numeral and eight characters"
                nameView.error = ""
            }

            INVALID_PASSWORD_EMAIL_NAME -> {
                emailView.error = "Invalid Email address"
                passwordView.error = "Use at least one numeral and eight characters"
                nameView.error = "Name must be equal or greater 2 characters"
            }
        }
    }
}