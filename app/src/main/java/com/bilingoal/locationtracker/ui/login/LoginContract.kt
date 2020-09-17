package com.bilingoal.locationtracker.ui.login

import com.bilingoal.locationtracker.base.BaseContract

interface LoginContract {
    interface Presenter {
        fun authenticate(email : String, password: String)
    }
    interface View : BaseContract.View {
        fun render(state: LoginState)
    }

}