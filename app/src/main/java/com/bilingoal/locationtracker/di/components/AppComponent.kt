package com.bilingoal.locationtracker.di.components

import android.app.Application
import com.bilingoal.locationtracker.base.BaseApplication
import com.bilingoal.locationtracker.di.modules.ActivityBuilderModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector


@Component(
    modules = [
        AndroidInjectionModule::class,
        ActivityBuilderModule::class
    ]
)
interface AppComponent : AndroidInjector<BaseApplication> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application?): AppComponent?
    }
}