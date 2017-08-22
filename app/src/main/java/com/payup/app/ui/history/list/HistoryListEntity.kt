package com.payup.app.ui.history.list

import com.payup.model.TransactionGraph

sealed class HistoryListEntity {
    data class GraphList(val items: List<TransactionGraph>): HistoryListEntity()
    data class TransactionEntry(val transaction: com.payup.model.Transaction): HistoryListEntity()
}
