package com.bilingoal.locationtracker.ui.registration

import com.bilingoal.locationtracker.ui.base.BaseState
import com.bilingoal.locationtracker.dto.UserAccount

sealed class RegistrationState : BaseState {
    object LoadingState: RegistrationState()
    data class FinishState(val userAccount: UserAccount?) : RegistrationState()
    data class ErrorState(val error: Throwable): RegistrationState()
}
