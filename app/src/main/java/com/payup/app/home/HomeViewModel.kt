package com.payup.app.home

import com.payup.app.Navigator
import com.payup.model.User
import io.reactivex.Observable


class HomeViewModel(
        private val navigator: Navigator
) {
    fun user(): Observable<User> {
        return Observable.just(User("Doddle Clark", "doddleoddle@gmail.com"))
    }

    fun sendPayment() {
        navigator.goToPaymentContacts()
    }
}
