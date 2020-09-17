package com.bilingoal.locationtracker.ui.registration

import com.bilingoal.locationtracker.firebase.createUserAccount
import io.reactivex.rxjava3.core.Observable

class RegistrationInteractorImpl : RegistrationInteractor {

    override fun createUser(name: String, email: String, password: String): Observable<RegistrationState> {
        val newUser = createUserAccount {
            this.name = name
            this.email = email
            this.password = password
        }

        return newUser.map<RegistrationState> {
            RegistrationState.FinishState(it)
        }.onErrorReturn {
            RegistrationState.ErrorState(it)
        }
    }
}