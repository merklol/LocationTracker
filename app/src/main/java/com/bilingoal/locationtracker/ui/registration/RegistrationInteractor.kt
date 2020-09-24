package com.bilingoal.locationtracker.ui.registration

import com.bilingoal.locationtracker.ui.base.Interactor
import com.bilingoal.locationtracker.models.network.createUserAccount
import com.bilingoal.locationtracker.models.preferences.UserPreferences
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class RegistrationInteractor @Inject constructor(
    private val userPreferences: UserPreferences) : Interactor<String, Observable<RegistrationState>> {

    override fun execute(vararg params: String): Observable<RegistrationState> {
        val (name, email, password) = params

        val newUser = createUserAccount {
            this.name = name
            this.email = email
            this.password = password
        }
        return newUser.doOnNext {
            userPreferences.saveUserCredentials(it)
        }.map<RegistrationState> {
            RegistrationState.FinishState(it)
        }.onErrorReturn {
            RegistrationState.ErrorState(it)
        }
    }
}