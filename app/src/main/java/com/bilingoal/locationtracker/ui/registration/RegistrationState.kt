package com.bilingoal.locationtracker.ui.registration

import com.bilingoal.locationtracker.dto.UserAccount
import com.bilingoal.locationtracker.ui.login.LoginState

sealed class RegistrationState {
    object LoadingState: RegistrationState()
    data class InvalidInputFormatState(val errorType: Int): RegistrationState()
    data class FinishState(val userAccount: UserAccount?) : RegistrationState()
    data class ErrorState(val error: Throwable): RegistrationState()
}
