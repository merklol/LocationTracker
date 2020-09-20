package com.bilingoal.locationtracker.ui.base

interface Interactor<Input, Output> {
    fun execute(vararg params: Input) : Output
}