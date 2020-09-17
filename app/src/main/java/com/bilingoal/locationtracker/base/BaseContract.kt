package com.bilingoal.locationtracker.base

interface BaseContract {
    interface Presenter<T : View?> {
        fun subscribe(view: T)
        fun unsubscribe()
    }

    interface View
}