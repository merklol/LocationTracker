package com.bilingoal.locationtracker.di.modules

import androidx.lifecycle.ViewModel
import com.bilingoal.locationtracker.di.annotations.ViewModelKey
import com.bilingoal.locationtracker.ui.registration.RegistrationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class RegistrationModule {
    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    abstract fun bindRegistrationViewModel(registrationViewModel: RegistrationViewModel): ViewModel
}