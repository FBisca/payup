package com.payup.app.ui.history

import android.support.v7.util.DiffUtil
import com.payup.app.ui.history.list.HistoryListEntity

class HistoryDiffCalback(
        private val oldList: List<HistoryListEntity>,
        private val newList: List<HistoryListEntity>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areItemsTheSame(oldItemPosition, newItemPosition)
    }
}
