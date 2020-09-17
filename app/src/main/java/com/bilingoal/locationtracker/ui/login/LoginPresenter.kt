package com.bilingoal.locationtracker.ui.login

import com.bilingoal.locationtracker.base.BasePresenter
import com.bilingoal.locationtracker.utils.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class LoginPresenter(private val interactor: LoginInteractor)
    : BasePresenter<LoginContract.View>(), LoginContract.Presenter {

    override fun authenticate(email: String, password: String) {
        when {
            !password.isValidPasswordFormat() && !email.isValidEmailFormat() ->
                view?.render(LoginState.InvalidInputFormatState(INVALID_PASSWORD_EMAIL))

            !password.isValidPasswordFormat() ->
                view?.render(LoginState.InvalidInputFormatState(INVALID_PASSWORD))

            !email.isValidEmailFormat() ->
                view?.render(LoginState.InvalidInputFormatState(INVALID_EMAIL))

            else -> performAuthentication(email, password)
        }
    }

    private fun performAuthentication(email: String, password: String) {
        interactor.authenticate(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view?.render(LoginState.LoadingState) }
            .doOnNext { view?.render(it) }
            .subscribe()
    }
}