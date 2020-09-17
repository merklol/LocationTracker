package com.bilingoal.locationtracker.base

import com.bilingoal.locationtracker.di.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class BaseApplication : DaggerApplication() {

    var injector: AndroidInjector<out DaggerApplication>? = null

    override fun onCreate() {
        injector = DaggerAppComponent.factory().create(this)
        super.onCreate()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>? = injector

}