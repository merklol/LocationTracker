package com.bilingoal.locationtracker.ui.registration

import com.bilingoal.locationtracker.ui.base.BaseViewModel
import com.bilingoal.locationtracker.dto.UserAccount
import com.bilingoal.locationtracker.models.router.Router
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val registrationInteractor: RegistrationInteractor,
    private val router: Router) : BaseViewModel<RegistrationState>(router) {

    fun createUser(name: String, email : String, password: String) {
        registrationInteractor.execute(name, email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { mutableState.value = RegistrationState.LoadingState }
            .subscribe { mutableState.value = it }
    }

    fun goToMainFragment(userAccount: UserAccount?) {
        router.navigateTo(RegistrationFragmentDirections.actionRegistrationFragmentToMainFragment(userAccount))
    }
}