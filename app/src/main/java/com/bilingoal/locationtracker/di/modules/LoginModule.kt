package com.bilingoal.locationtracker.di.modules

import androidx.lifecycle.ViewModel
import com.bilingoal.locationtracker.di.annotations.ViewModelKey
import com.bilingoal.locationtracker.ui.login.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class LoginModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

}