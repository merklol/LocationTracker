package com.bilingoal.locationtracker.ui.registration

import com.bilingoal.locationtracker.ui.base.BaseViewModel
import com.bilingoal.locationtracker.dto.UserAccount
import com.bilingoal.locationtracker.models.router.Router
import com.bilingoal.locationtracker.models.validators.CredentialsValidation
import com.bilingoal.locationtracker.models.validators.CredentialsValidator
import com.bilingoal.locationtracker.models.validators.VALID_INPUT_FORMAT
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val registrationInteractor: RegistrationInteractor,
    private val router: Router,
    private val validator: CredentialsValidator) : BaseViewModel<RegistrationState>(router) {

    private var isValidName = false
    private var isValidEMail = false
    private var isValidPassword = false

    fun createUser(name: String, email : String, password: String) {
        disposablesBag.add(
            registrationInteractor.execute(name, email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mutableState.value = RegistrationState.LoadingState }
                .subscribe { mutableState.value = it }
        )
    }

    fun navigateToMainScreen(userAccount: UserAccount?) {
        router.navigateToMainFromRegistration(userAccount)
    }

    fun validateName(value: String) {
        val res = validator.execute(value, CredentialsValidation.NAME)
        isValidName = res == VALID_INPUT_FORMAT
        mutableState.value = RegistrationState.InvalidNameFormatState(res)
    }

    fun validateEmail(value: String) {
        val res = validator.execute(value, CredentialsValidation.EMAIL)
        isValidEMail = res == VALID_INPUT_FORMAT
        mutableState.value = RegistrationState.InvalidEmailFormatState(res)
    }

    fun validatePassword(value: String) {
        val res = validator.execute(value, CredentialsValidation.PASSWORD)
        isValidPassword = res == VALID_INPUT_FORMAT
        mutableState.value = RegistrationState.InvalidPasswordFormatState(res)
    }

    fun registrationState() {
       if(isValidName && isValidEMail && isValidPassword) {
           mutableState.value = RegistrationState.RegistrationAvailable
       } else {
           mutableState.value = RegistrationState.RegistrationUnavailable
       }
    }
}