package com.payup.app.ui.screens.history

import com.payup.app.ui.entities.HistoryListEntity
import com.payup.data.manager.SchedulerManager
import com.payup.data.repository.UserRepository
import com.payup.di.ActivityScope
import com.payup.model.Transaction
import com.payup.model.TransactionGraph
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@ActivityScope
class HistoryViewModel @Inject constructor(
        private val userRepository: UserRepository,
        private val schedulerManager: SchedulerManager
) {

    val viewState: BehaviorSubject<ViewState> = BehaviorSubject.createDefault(ViewState.Loading)

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

    private fun historyList(): Observable<List<HistoryListEntity>> {
        return userRepository.getTransactionHistory()
                .map { convertToListEntity(it) }
                .subscribeOn(schedulerManager.workThread())
                .observeOn(schedulerManager.mainThread())
                .doOnSubscribe { viewState.onNext(ViewState.Loading) }
                .toObservable()
    }

    private fun convertToListEntity(list: List<Transaction>): List<HistoryListEntity> {
        val mutableList = mutableListOf<HistoryListEntity>()
        mutableList.add(createGraphEntity(list))
        mutableList.addAll(createEntries(list))
        return mutableList
    }

    private fun createEntries(list: List<Transaction>) = list.sortedByDescending { it.date }
            .map { HistoryListEntity.TransactionEntry(it) }

    private fun createGraphEntity(list: List<Transaction>): HistoryListEntity.GraphList {
        val totalSpend = list.sumByDouble { it.value }

        val graphList = list.groupBy { it.user }.map {
            val totalPerUser = it.value.sumByDouble { it.value }
            val percentage = totalPerUser * 100 / totalSpend
            TransactionGraph(it.key, totalPerUser, percentage.toInt())
        }.sortedByDescending { it.totalValue }

        return HistoryListEntity.GraphList(graphList)
    }

    sealed class ViewState {
        object Loading : ViewState()
        object Error : ViewState()
        data class ListState(val list: List<HistoryListEntity>) : ViewState()
    }
}
