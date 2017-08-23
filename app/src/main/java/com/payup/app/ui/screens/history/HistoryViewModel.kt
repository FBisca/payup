package com.payup.app.ui.screens.history

import com.payup.data.OpenForTests
import com.payup.data.manager.SchedulerManager
import com.payup.data.repository.UserRepository
import com.payup.di.ActivityScope
import com.payup.model.Transaction
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

@OpenForTests
@ActivityScope
class HistoryViewModel @Inject constructor(
        private val userRepository: UserRepository,
        private val schedulerManager: SchedulerManager,
        initialViewState: ViewState
) {

    val viewState: BehaviorSubject<ViewState> = BehaviorSubject.createDefault(initialViewState)

    private val disposable = CompositeDisposable()

    fun viewCreated() {
        if (viewState.value !is ViewState.ListState) {
            retrieveHistoryList()
        }
    }

    fun viewDestroyed() {
        disposable.dispose()
    }

    fun tryAgainClick() {
        retrieveHistoryList()
    }

    private fun retrieveHistoryList() {
        historyList()
                .subscribe(
                        { viewState.onNext(ViewState.ListState(it)) },
                        { viewState.onNext(ViewState.Error) }
                )
                .apply { disposable.add(this) }
    }

    private fun historyList(): Observable<List<Transaction>> {
        return userRepository.getTransactionHistory()
                .subscribeOn(schedulerManager.workThread())
                .observeOn(schedulerManager.mainThread())
                .doOnSubscribe { viewState.onNext(ViewState.Loading) }
                .toObservable()
    }

    sealed class ViewState {
        object Loading : ViewState()
        object Error : ViewState()
        data class ListState(val list: List<Transaction>) : ViewState()
    }
}
