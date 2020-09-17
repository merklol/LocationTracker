package com.bilingoal.locationtracker.ui.login

import io.reactivex.rxjava3.core.Observable

interface LoginInteractor {
    fun authenticate(email: String, password: String): Observable<LoginState>
}