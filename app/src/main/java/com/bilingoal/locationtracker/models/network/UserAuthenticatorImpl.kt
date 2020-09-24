package com.bilingoal.locationtracker.models.network

import android.util.Log
import com.bilingoal.locationtracker.dto.UserAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter

private const val TAG = "UserAuthenticator"

class UserAuthenticatorImpl : UserAuthenticator {
    private val auth = Firebase.auth
    private val db = Firebase.firestore
    
    override lateinit var email: String
    override lateinit var password: String
    override val currentUser: FirebaseUser? = auth.currentUser

    override fun authenticate() : Observable<UserAccount> {
        return Observable.create { emitter ->
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                when {
                    task.isSuccessful -> collectUserData(emitter)
                    else ->  setTaskError(task, emitter)
                }
            }
        }
    }

    private fun collectUserData(emitter: @NonNull ObservableEmitter<UserAccount>) {
        val docRef = db.collection(COLLECTION_NAME).document(email)
        docRef.get().addOnSuccessListener {
            val user = it.toObject<UserAccount>()
            emitter.onNext(user)
        }.addOnFailureListener {Log.e(TAG, it.localizedMessage ?: "Something went wrong") }
    }

    private fun setTaskError(task: Task<AuthResult>, emitter: @NonNull ObservableEmitter<UserAccount>) {
        val res = when(task.exception) {
            is FirebaseAuthInvalidUserException -> "The email address is is invalid or the user does not exist."
            is FirebaseAuthInvalidCredentialsException -> "The password is invalid or the user does not have a password."
            else -> "Something went wrong"
        }
        emitter.onError(Throwable(res))
        Log.w(TAG, "signInWithEmail:failure", task.exception)
    }
}

fun authenticateUser(init: UserAuthenticator.() -> Unit): Observable<UserAccount> {
    val userAuthenticator: UserAuthenticator = UserAuthenticatorImpl()
    userAuthenticator.init()

    return userAuthenticator.authenticate()
}
