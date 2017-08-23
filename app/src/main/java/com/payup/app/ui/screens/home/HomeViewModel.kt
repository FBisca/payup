package com.payup.app.ui.screens.home

import android.util.Log
import com.payup.app.Navigator
import com.payup.data.OpenForTests
import com.payup.data.manager.SchedulerManager
import com.payup.data.manager.TokenManager
import com.payup.data.repository.UserRepository
import com.payup.di.ActivityScope
import com.payup.model.User
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@OpenForTests
@ActivityScope
class HomeViewModel @Inject constructor(
        private val navigator: Navigator,
        private val tokenManager: TokenManager,
        private val schedulerManager: SchedulerManager,
        private val userRepository: UserRepository
) {
    private val disposables = CompositeDisposable()

    fun viewCreated() {
        warmUpToken()
    }

    private fun warmUpToken() {
        tokenManager.getToken()
                .subscribeOn(schedulerManager.workThread())
                .subscribe(
                        { Log.d("HomeViewModel", "Token warmed") },
                        { Log.e("HomeViewModel", "Token warmUp error", it) }
                ).apply { disposables.add(this) }
    }

    fun viewDestroyed() {
        disposables.dispose()
    }

    fun user(): Observable<User> {
        return userRepository.getUser().toObservable()
    }

    fun sendPayment() {
        navigator.goToPaymentContacts()
    }

    fun history() {
        navigator.goToHistoryActivity()
    }
}
