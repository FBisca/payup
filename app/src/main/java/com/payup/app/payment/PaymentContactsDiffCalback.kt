package com.payup.app.payment

import android.support.v7.util.DiffUtil
import com.payup.model.Contact

class PaymentContactsDiffCalback(
        private val oldList: List<Contact>,
        private val newList: List<Contact>
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
