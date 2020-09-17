package com.bilingoal.locationtracker.di.modules

import com.bilingoal.locationtracker.ui.login.LoginInteractorImpl
import com.bilingoal.locationtracker.ui.login.LoginPresenter
import dagger.Module
import dagger.Provides

@Module
class LoginModule {

    @Provides
    fun provideLoginPresenter() : LoginPresenter {
        return LoginPresenter(LoginInteractorImpl())
    }
}