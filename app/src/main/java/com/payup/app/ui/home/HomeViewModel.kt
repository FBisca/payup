package com.payup.app.ui.home

import com.payup.app.components.Navigator
import com.payup.data.repository.UserRepository
import com.payup.di.ActivityScope
import com.payup.model.User
import io.reactivex.Observable
import javax.inject.Inject

@ActivityScope
class HomeViewModel @Inject constructor(
        private val navigator: Navigator,
        private val userRepository: UserRepository
) {
    fun user(): Observable<User> {
        return userRepository.getUser().toObservable()
    }

    fun sendPayment() {
        navigator.goToPaymentContacts()
    }
}
