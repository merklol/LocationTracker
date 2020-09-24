package com.bilingoal.locationtracker.models.network

import com.bilingoal.locationtracker.dto.UserAccount
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.reactivex.rxjava3.core.Observable

interface UserAuthenticator {
    var email: String
    var password: String
    val currentUser: FirebaseUser?

    companion object {
        fun isUserAuthenticated() = Firebase.auth.currentUser != null
        fun signOut() = Firebase.auth.signOut()
    }

    fun authenticate(): Observable<UserAccount>
}