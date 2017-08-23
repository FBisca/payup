package com.payup.app.ui.entities

import com.payup.model.Transaction
import com.payup.model.TransactionGraph

sealed class HistoryListEntity {
    data class GraphList(val items: List<TransactionGraph>): HistoryListEntity()
    data class TransactionEntry(val transaction: Transaction): HistoryListEntity()

    companion object {
        fun convertToListEntity(list: List<Transaction>): List<HistoryListEntity> {
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
    }
}
