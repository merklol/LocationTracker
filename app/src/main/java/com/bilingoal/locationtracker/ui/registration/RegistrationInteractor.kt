package com.bilingoal.locationtracker.ui.registration

import io.reactivex.rxjava3.core.Observable

interface RegistrationInteractor {
    fun createUser(name: String, email: String, password: String): Observable<RegistrationState>
}