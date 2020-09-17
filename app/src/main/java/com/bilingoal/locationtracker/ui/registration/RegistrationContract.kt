package com.bilingoal.locationtracker.ui.registration

import com.bilingoal.locationtracker.base.BaseContract

interface RegistrationContract {
    interface Presenter {
        fun createUser(name: String, email : String, password: String)
    }
    interface View : BaseContract.View {
        fun render(state: RegistrationState)
    }

}