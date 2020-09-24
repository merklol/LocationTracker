package com.bilingoal.locationtracker.ui.login

import com.bilingoal.locationtracker.dto.UserAccount
import com.bilingoal.locationtracker.ui.base.BaseView

interface LoginView : BaseView {
    fun renderDefaultState()
    fun renderLoadingState()
    fun renderFinishState(userAccount: UserAccount?)
    fun renderErrorState(error: Throwable)
    fun renderInvalidInputFormatState(errors: List<Int>)
}