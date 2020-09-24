package com.bilingoal.locationtracker.di.modules

import com.bilingoal.locationtracker.ui.HostActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [
        FragmentBuilderModule::class,
        LoginModule::class,
        RegistrationModule::class
    ])
    abstract fun contributeMainActivity(): HostActivity

}