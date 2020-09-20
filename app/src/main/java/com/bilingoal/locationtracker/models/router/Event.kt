package com.bilingoal.locationtracker.models.router

open class Event<out T>(private val content: T) {
    var hasBeenHandled = false
        private set

    fun consume(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content
}