package com.bilingoal.locationtracker.models.preferences

import android.app.Application
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.bilingoal.locationtracker.dto.UserAccount
import javax.inject.Inject

private const val USER_CREDENTIALS = "user-credentials"
private const val USER_NAME= "user-name"
private const val USER_EMAIL = "user-email"
private const val USER_PASSWORD = "user-password"
private const val USER_UID = "user-uid"
private const val UNKNOWN_USER_CREDENTIALS = "unknown-user-credentials"

class UserPreferences @Inject constructor(application: Application?) {
    private val preferences = EncryptedSharedPreferences.create(
        application!!,
        USER_CREDENTIALS,
        MasterKey.Builder(application, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun readUserCredentials(): UserAccount {
        if(!preferences.contains(USER_NAME) || !preferences.contains(USER_EMAIL)
            || !preferences.contains(USER_PASSWORD) || !preferences.contains(USER_UID)) {
            throw RuntimeException("User credentials aren't stored in the preferences")
        }

        val name = preferences.getString(USER_NAME, UNKNOWN_USER_CREDENTIALS)
        val email = preferences.getString(USER_EMAIL, UNKNOWN_USER_CREDENTIALS)
        val password = preferences.getString(USER_PASSWORD, UNKNOWN_USER_CREDENTIALS)
        val uid = preferences.getString(USER_UID, UNKNOWN_USER_CREDENTIALS)

        return UserAccount(email, name, password, uid)
    }

    fun saveUserCredentials(userAccount: UserAccount) {
        val editor = preferences.edit()
        editor.putString(USER_NAME, userAccount.name)
        editor.putString(USER_EMAIL, userAccount.email)
        editor.putString(USER_PASSWORD, userAccount.password)
        editor.putString(USER_UID, userAccount.uid)
        editor.apply()
    }

    fun clearUserCredentials() {
        preferences.edit().clear().apply()
    }
}