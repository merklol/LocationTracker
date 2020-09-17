package com.bilingoal.locationtracker.di.modules

import com.bilingoal.locationtracker.ui.login.LoginFragment
import com.bilingoal.locationtracker.ui.main.MainFragment
import com.bilingoal.locationtracker.ui.registration.RegistrationFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun contributeRegistrationFragment(): RegistrationFragment

    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment
}