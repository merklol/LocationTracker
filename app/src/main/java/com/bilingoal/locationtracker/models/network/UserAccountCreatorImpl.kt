package com.bilingoal.locationtracker.models.network

import android.util.Log
import com.bilingoal.locationtracker.dto.UserAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter

private const val TAG = "UserCreator"

const val COLLECTION_NAME = "users"
const val NAME = "name"
const val EMAIL = "email"
const val PASSWORD = "password"

class UserAccountCreatorImpl : UserAccountCreator {
    private val auth = Firebase.auth
    private val db = Firebase.firestore

    override lateinit var name: String
    override lateinit var email: String
    override lateinit var password: String

    override fun create(): Observable<UserAccount> {
        return Observable.create { emitter ->
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                when {
                    task.isSuccessful -> createRecordInFirestore(emitter)
                    else ->  setTaskError(task, emitter)
                }
            }
        }
    }

    private fun createRecordInFirestore(emitter: @NonNull ObservableEmitter<UserAccount>) {
        val user = hashMapOf(NAME to name, EMAIL to email, PASSWORD to password)
        db.collection(COLLECTION_NAME).document(email).set(user)
            .addOnSuccessListener {
                emitter.onNext(UserAccount(email, name, password))
                Log.d(TAG, "User is successfully added to the database")
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error adding document", e) }
    }

    private fun setTaskError(task: Task<AuthResult>, emitter: @NonNull ObservableEmitter<UserAccount>) {
        val res = when (task.exception) {
            is FirebaseAuthUserCollisionException -> "User with this email already exist."
            else -> "Something went wrong"
        }
        emitter.onError(Throwable(res))
        Log.w(TAG, "createUserWithEmail:failure", task.exception)
    }
}

fun createUserAccount(init: UserAccountCreator.() -> Unit): Observable<UserAccount> {
    val userAccountCreator = UserAccountCreatorImpl()
    userAccountCreator.init()

    return userAccountCreator.create()
}