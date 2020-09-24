package com.bilingoal.locationtracker.ui.registration

import com.bilingoal.locationtracker.ui.base.BaseState
import com.bilingoal.locationtracker.dto.UserAccount
import com.bilingoal.locationtracker.ui.base.Reducer

sealed class RegistrationState: BaseState {
    object LoadingState: RegistrationState()
    object RegistrationAvailable : RegistrationState()
    object RegistrationUnavailable : RegistrationState()
    data class InvalidNameFormatState(val res: Int): RegistrationState()
    data class InvalidEmailFormatState(val res: Int): RegistrationState()
    data class InvalidPasswordFormatState(val res: Int): RegistrationState()
    data class FinishState(val userAccount: UserAccount?): RegistrationState()
    data class ErrorState(val error: Throwable): RegistrationState()
}

class RegistrationReducer(private val view: RegistrationView): Reducer {
    override fun reduce(state: BaseState) {
        when (state) {
            is RegistrationState.RegistrationAvailable ->
                view.renderRegistrationAvailableState()
            is RegistrationState.RegistrationUnavailable ->
                view.renderRegistrationUnavailableState()
            is RegistrationState.LoadingState ->
                view.renderLoadingState()
            is RegistrationState.FinishState ->
                view.renderFinishState(state.userAccount)
            is RegistrationState.ErrorState ->
                view.renderErrorState(state.error)
            is RegistrationState.InvalidNameFormatState ->
                view.renderInvalidNameFormatState(state.res)
            is RegistrationState.InvalidEmailFormatState ->
                view.renderInvalidEmailFormatState(state.res)
            is RegistrationState.InvalidPasswordFormatState ->
                view.renderInvalidPasswordFormatState(state.res)
        }
    }
}
