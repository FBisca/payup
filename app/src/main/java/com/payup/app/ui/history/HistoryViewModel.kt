package com.payup.app.ui.history

import com.payup.app.ui.history.list.HistoryListEntity
import com.payup.data.manager.SchedulerManager
import com.payup.data.repository.UserRepository
import com.payup.di.ActivityScope
import com.payup.model.Transaction
import com.payup.model.TransactionGraph
import io.reactivex.Observable
import javax.inject.Inject

@ActivityScope
class HistoryViewModel @Inject constructor(
        private val userRepository: UserRepository,
        private val schedulerManager: SchedulerManager
) {

    fun historyList(): Observable<List<HistoryListEntity>> {
        return userRepository.getTransactionHistory()
                .subscribeOn(schedulerManager.workThread())
                .observeOn(schedulerManager.mainThread())
                .map { convertToListEntity(it) }
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
        }.sortedByDescending { it.percentage }

        return HistoryListEntity.GraphList(graphList)
    }
}
