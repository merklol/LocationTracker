package com.bilingoal.locationtracker.utils

import java.util.regex.Pattern

const val INVALID_NAME: Int = 0
const val INVALID_EMAIL: Int = 1
const val INVALID_PASSWORD: Int = 2
const val INVALID_PASSWORD_EMAIL: Int = 3
const val INVALID_PASSWORD_NAME: Int = 4
const val INVALID_PASSWORD_EMAIL_NAME: Int = 5

fun String.isValidPasswordFormat(): Boolean {
    val regex = Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$")
    return regex.matcher(this).matches()
}
fun String.isValidEmailFormat(): Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
fun String.isValidNameFormat(): Boolean = this.length >= 2