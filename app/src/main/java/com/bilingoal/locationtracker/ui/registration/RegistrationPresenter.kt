package com.bilingoal.locationtracker.ui.registration

import com.bilingoal.locationtracker.base.BasePresenter
import com.bilingoal.locationtracker.utils.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class RegistrationPresenter(private val interactor: RegistrationInteractor)
    : BasePresenter<RegistrationContract.View>(), RegistrationContract.Presenter {

    override fun createUser(name: String, email : String, password: String) {
        when {
            !name.isValidNameFormat() && !password.isValidPasswordFormat()
                    && !email.isValidEmailFormat() ->
                view?.render(RegistrationState.InvalidInputFormatState(INVALID_PASSWORD_EMAIL_NAME))

            !password.isValidPasswordFormat() && !name.isValidNameFormat() ->
                view?.render(RegistrationState.InvalidInputFormatState(INVALID_PASSWORD_EMAIL))

            !password.isValidPasswordFormat() && !email.isValidEmailFormat() ->
                view?.render(RegistrationState.InvalidInputFormatState(INVALID_PASSWORD_EMAIL))

            !password.isValidPasswordFormat() ->
                view?.render(RegistrationState.InvalidInputFormatState(INVALID_PASSWORD))

            !email.isValidEmailFormat() ->
                view?.render(RegistrationState.InvalidInputFormatState(INVALID_EMAIL))

            !name.isValidNameFormat() ->
                view?.render(RegistrationState.InvalidInputFormatState(INVALID_NAME))

            else -> performUserCreation(name, email, password)
        }
    }

    private fun performUserCreation(name: String, email : String, password: String) =
        interactor.createUser(name, email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view?.render(RegistrationState.LoadingState) }
            .doOnNext { view?.render(it) }
            .subscribe()
}