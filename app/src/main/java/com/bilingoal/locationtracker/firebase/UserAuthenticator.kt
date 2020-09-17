package com.bilingoal.locationtracker.firebase

import com.bilingoal.locationtracker.dto.UserAccount
import io.reactivex.rxjava3.core.Observable

interface UserAuthenticator {
    var email: String
    var password: String

    fun authenticate(): Observable<UserAccount>
}