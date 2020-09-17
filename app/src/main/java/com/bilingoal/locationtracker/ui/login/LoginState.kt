package com.bilingoal.locationtracker.ui.login

import com.bilingoal.locationtracker.dto.UserAccount

sealed class LoginState {
    object LoadingState: LoginState()
    data class InvalidInputFormatState(val errorType: Int): LoginState()
    data class FinishState(val userAccount: UserAccount?) : LoginState()
    data class ErrorState(val error: Throwable): LoginState()
}
