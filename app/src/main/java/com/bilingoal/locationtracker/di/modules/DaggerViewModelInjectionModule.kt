package com.bilingoal.locationtracker.di.modules

import androidx.lifecycle.ViewModelProvider
import com.bilingoal.locationtracker.di.utils.DaggerViewModelFactory
import dagger.Binds
import dagger.Module


@Module
abstract class DaggerViewModelInjectionModule {
    @Binds
    abstract fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory
}