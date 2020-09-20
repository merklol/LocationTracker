package com.bilingoal.locationtracker.models.validators

import android.util.Patterns
import com.bilingoal.locationtracker.dto.UserInput
import java.util.regex.Pattern

const val INVALID_NAME: Int = 0
const val INVALID_EMAIL: Int = 1
const val INVALID_PASSWORD: Int = 2

enum class CredentialsValidation(private val _type: Int) : Validation {

    EMAIL(INVALID_EMAIL) {
        override fun validate(input: UserInput): Boolean {
            return input.email.isValidEmailFormat()
        }

        override fun validate(input: String): Boolean {
            return input.isValidEmailFormat()
        }
    },
    PASSWORD(INVALID_PASSWORD) {
        override fun validate(input: UserInput): Boolean {
            return input.password.isValidPasswordFormat()
        }

        override fun validate(input: String): Boolean {
            return input.isValidPasswordFormat()
        }
    },
    NAME(INVALID_NAME) {
        override fun validate(input: UserInput): Boolean {
            return input.name.isValidNameFormat()
        }

        override fun validate(input: String): Boolean {
            return input.isValidNameFormat()
        }
    };

    override val type: Int get() = _type
}

fun String.isValidPasswordFormat(): Boolean {
    return Pattern
        .compile("^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$")
        .matcher(this).matches() && this.isNotEmpty()
}

fun String.isValidEmailFormat(): Boolean = Patterns.EMAIL_ADDRESS.matcher(this).matches() && this.isNotEmpty()
fun String.isValidNameFormat(): Boolean = this.length >= 2