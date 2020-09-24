package com.bilingoal.locationtracker.ui.base

import com.bilingoal.locationtracker.ui.login.LoginReducer
import com.bilingoal.locationtracker.ui.login.LoginView
import com.bilingoal.locationtracker.ui.registration.RegistrationReducer
import com.bilingoal.locationtracker.ui.registration.RegistrationView
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ReducerFactory {

    operator fun provideDelegate(thisRef: Any, property: KProperty<*>): ReadOnlyProperty<BaseView, Reducer> {
        return ReadOnlyProperty { view, _ ->
            when (view) {
                is LoginView -> LoginReducer(view)
                is RegistrationView -> RegistrationReducer(view)
                else -> throw RuntimeException("$view does not match any reference")
            }
        }
    }
}

fun reducers(): ReducerFactory = ReducerFactory()
