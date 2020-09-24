package com.bilingoal.locationtracker.ui.login

import com.bilingoal.locationtracker.ui.base.BaseViewModel
import com.bilingoal.locationtracker.dto.UserAccount
import com.bilingoal.locationtracker.models.router.Router
import com.bilingoal.locationtracker.models.validators.CredentialsValidation
import com.bilingoal.locationtracker.models.validators.CredentialsValidator
import com.bilingoal.locationtracker.dto.UserInput
import com.bilingoal.locationtracker.models.network.UserAuthenticator
import com.bilingoal.locationtracker.models.preferences.UserPreferences
import com.bilingoal.locationtracker.models.validators.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginInteractor: LoginInteractor,
    private val userPreferences: UserPreferences,
    private val router: Router,
    private val validator: CredentialsValidator) : BaseViewModel<LoginState>(router) {

    fun authenticate(email: String, password: String) {
        if(email.isValidEmailFormat() && password.isValidPasswordFormat()) {
            performAuthentication(email, password)
        } else {
            val errors = validator.execute(UserInput(email, password),
                CredentialsValidation.EMAIL, CredentialsValidation.PASSWORD)

            mutableState.postValue(LoginState.InvalidInputFormatState(errors))
        }
    }

    fun autoLogin() {
        if(UserAuthenticator.isUserAuthenticated()) {
            mutableState.postValue(LoginState.LoadingState)
            router.navigateToMainFromSignIn(userPreferences.readUserCredentials())
        } else {
            mutableState.postValue(LoginState.DefaultState)
        }
    }

    fun navigateToRegistration() {
        router.navigateToRegistration()
        mutableState.value = LoginState.DefaultState
    }

    fun navigateToMainScreen(userAccount: UserAccount?) {
        router.navigateToMainFromSignIn(userAccount)
    }

    private fun performAuthentication(email: String, password: String) {
        disposablesBag.add(
            loginInteractor.execute(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { mutableState.value = LoginState.LoadingState }
            .subscribe { mutableState.value = it }
        )
    }
}