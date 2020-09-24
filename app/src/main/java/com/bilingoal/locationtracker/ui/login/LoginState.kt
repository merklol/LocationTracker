package com.bilingoal.locationtracker.ui.login

import com.bilingoal.locationtracker.ui.base.BaseState
import com.bilingoal.locationtracker.dto.UserAccount
import com.bilingoal.locationtracker.ui.base.Reducer

sealed class LoginState: BaseState {

    object DefaultState: LoginState()
    object LoadingState: LoginState()
    class InvalidInputFormatState(val errors: List<Int>): LoginState()
    data class FinishState(val userAccount: UserAccount?) : LoginState()
    data class ErrorState(val error: Throwable): LoginState()
}

class LoginReducer(private val loginView: LoginView): Reducer {
    override fun reduce(state: BaseState) {
        when(state)  {
            is LoginState.DefaultState -> loginView.renderDefaultState()
            is LoginState.LoadingState -> loginView.renderLoadingState()
            is LoginState.FinishState -> loginView.renderFinishState(state.userAccount)
            is LoginState.ErrorState -> loginView.renderErrorState(state.error)
            is LoginState.InvalidInputFormatState -> loginView.renderInvalidInputFormatState(state.errors)
        }
    }
}
