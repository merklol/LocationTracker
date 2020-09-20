package com.bilingoal.locationtracker.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.bilingoal.locationtracker.models.router.Router
import com.bilingoal.locationtracker.models.router.Event
import io.reactivex.rxjava3.disposables.CompositeDisposable

open class BaseViewModel<VS>(private val router: Router) : ViewModel() {
    protected val disposablesBag = CompositeDisposable()

    protected val mutableState: MutableLiveData<VS> = MutableLiveData()

    val state: LiveData<VS>
        get() = mutableState

    val navigationEvent: LiveData<Event<NavController.() -> Any>>
        get() = router.navigationEvent

    override fun onCleared() {
        super.onCleared()
        disposablesBag.clear()
    }
}