package com.bilingoal.locationtracker.ui.login

import com.bilingoal.locationtracker.firebase.authenticateUser
import io.reactivex.rxjava3.core.Observable

class LoginInteractorImpl : LoginInteractor {
    override fun authenticate(email: String, password: String): Observable<LoginState> {
        val user = authenticateUser {
            this.email = email
            this.password = password
        }
        return user.map<LoginState> { LoginState.FinishState(it) }.onErrorReturn { LoginState.ErrorState(it) }
    }
}