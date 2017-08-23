package com.payup.app.ui.screens.payment

import com.payup.app.Navigator
import com.payup.app.ui.screens.payment.ConfirmationViewModel.ViewState.*
import com.payup.data.OpenForTests
import com.payup.data.manager.SchedulerManager
import com.payup.data.repository.UserRepository
import com.payup.di.ActivityScope
import com.payup.model.Contact
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

@OpenForTests
@ActivityScope
class ConfirmationViewModel @Inject constructor(
        val contact: Contact,
        val value: Double,
        private val navigator: Navigator,
        private val userRepository: UserRepository,
        private val schedulerManager: SchedulerManager
) {

    val viewState: BehaviorSubject<ViewState> = BehaviorSubject.createDefault(Idle)
    private val disposables = CompositeDisposable()

    fun viewDestroyed() {
        disposables.dispose()
    }

    fun confirmClick() {
        if (viewState.value != Success) {
            userRepository.sendPayment(value, contact)
                    .subscribeOn(schedulerManager.workThread())
                    .observeOn(schedulerManager.mainThread())
                    .doOnSubscribe { viewState.onNext(Loading) }
                    .subscribe(
                            { viewState.onNext(Success) },
                            { viewState.onNext(Error) }
                    )
                    .apply { disposables.add(this) }
        } else {
            navigator.returnToHome()
        }
    }

    sealed class ViewState {
        object Idle : ViewState()
        object Loading : ViewState()
        object Success : ViewState()
        object Error : ViewState()
    }
}
