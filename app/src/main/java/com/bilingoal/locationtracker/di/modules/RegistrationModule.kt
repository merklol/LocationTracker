package com.bilingoal.locationtracker.di.modules

import com.bilingoal.locationtracker.ui.registration.RegistrationInteractorImpl
import com.bilingoal.locationtracker.ui.registration.RegistrationPresenter
import dagger.Module
import dagger.Provides

@Module
class RegistrationModule {

    @Provides
    fun provideRegistrationPresenter(): RegistrationPresenter {
        return RegistrationPresenter(RegistrationInteractorImpl())
    }
}