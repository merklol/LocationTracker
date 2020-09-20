package com.bilingoal.locationtracker.ui.login

import com.bilingoal.locationtracker.ui.base.BaseState
import com.bilingoal.locationtracker.dto.UserAccount

sealed class LoginState : BaseState {
    object LoadingState: LoginState()
    class InvalidInputFormatState(val errors: List<Int>): LoginState()
    data class FinishState(val userAccount: UserAccount?) : LoginState()
    data class ErrorState(val error: Throwable): LoginState()
}
