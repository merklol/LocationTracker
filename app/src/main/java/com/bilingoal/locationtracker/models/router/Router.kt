package com.bilingoal.locationtracker.models.router

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import javax.inject.Inject

class Router @Inject constructor() {
    private val _navigationEvent: MutableLiveData<Event<NavController.() -> Any>> = MutableLiveData()

    val navigationEvent: LiveData<Event<NavController.() -> Any>>
        get() = _navigationEvent

    fun navigateTo(directions: NavDirections) {
        withNavController { navigate(directions) }
    }

    private fun withNavController(block: NavController.() -> Any) {
        _navigationEvent.postValue(Event(block))
    }
}