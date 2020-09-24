package com.bilingoal.locationtracker.ui.registration

import com.bilingoal.locationtracker.dto.UserAccount
import com.bilingoal.locationtracker.ui.base.BaseView

interface RegistrationView: BaseView {
    fun renderLoadingState()
    fun renderRegistrationAvailableState()
    fun renderRegistrationUnavailableState()
    fun renderFinishState(userAccount: UserAccount?)
    fun renderErrorState(error: Throwable)
    fun renderInvalidNameFormatState(res: Int)
    fun renderInvalidEmailFormatState(res: Int)
    fun renderInvalidPasswordFormatState(res: Int)
}