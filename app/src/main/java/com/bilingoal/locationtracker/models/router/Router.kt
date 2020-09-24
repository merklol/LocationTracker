package com.bilingoal.locationtracker.models.router

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.bilingoal.locationtracker.dto.UserAccount
import com.bilingoal.locationtracker.ui.login.LoginFragmentDirections
import com.bilingoal.locationtracker.ui.registration.RegistrationFragmentDirections
import javax.inject.Inject

class Router @Inject constructor() {
    private val _navigationEvent: MutableLiveData<Event<NavController.() -> Any>> = MutableLiveData()

    val navigationEvent: LiveData<Event<NavController.() -> Any>>
        get() = _navigationEvent

    fun navigateTo(directions: NavDirections) {
        withNavController { navigate(directions) }
    }

    fun navigateToRegistration() {
        withNavController {
            navigate(LoginFragmentDirections.actionLoginFragmentToRegistrationFragment())
        }
    }

    fun navigateToMainFromSignIn(userAccount: UserAccount?) {
        withNavController {
            navigate(LoginFragmentDirections.actionLoginFragmentToMainFragment(userAccount))
        }
    }

    fun navigateToMainFromRegistration(userAccount: UserAccount?) {
        withNavController {
            navigate(RegistrationFragmentDirections.actionRegistrationFragmentToMainFragment(userAccount))
        }
    }

    private fun withNavController(block: NavController.() -> Any) {
        _navigationEvent.postValue(Event(block))
    }
}