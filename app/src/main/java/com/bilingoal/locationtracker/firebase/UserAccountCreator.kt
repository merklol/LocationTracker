package com.bilingoal.locationtracker.firebase

import com.bilingoal.locationtracker.dto.UserAccount
import io.reactivex.rxjava3.core.Observable

interface UserAccountCreator {
    var name: String
    var email: String
    var password: String

    fun create(): Observable<UserAccount>
}