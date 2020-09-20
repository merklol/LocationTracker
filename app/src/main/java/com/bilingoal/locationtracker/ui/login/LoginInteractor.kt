package com.bilingoal.locationtracker.ui.login

import com.bilingoal.locationtracker.models.network.authenticateUser
import com.bilingoal.locationtracker.ui.base.Interactor
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class LoginInteractor @Inject constructor(): Interactor<String, Observable<LoginState>> {

    override fun execute(vararg params: String): Observable<LoginState> {
        val(email, password) = params

        val user = authenticateUser {
            this.email = email
            this.password = password
        }
        return user.map<LoginState> { LoginState.FinishState(it) }.onErrorReturn { LoginState.ErrorState(it) }
    }
}