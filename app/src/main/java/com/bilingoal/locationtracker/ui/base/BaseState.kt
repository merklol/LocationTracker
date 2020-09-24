package com.bilingoal.locationtracker.ui.base

interface BaseState {
    fun accept(reducer: Reducer) = reducer.reduce(this)
}