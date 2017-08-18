package com.payup.app.payment

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.payup.databinding.ListContactBinding
import com.payup.model.Contact

class PaymentContactsAdapter : RecyclerView.Adapter<PaymentContactsAdapter.ViewHolder>() {

    var items = emptyList<Contact>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = ListContactBinding.inflate(inflater, parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bind(items[position])
    }

    override fun getItemCount() = items.size

    class ViewHolder(
            private val binding: ListContactBinding
    ) : RecyclerView.ViewHolder(binding.root) {

         fun bind(contact: Contact) {
             binding.contact = contact
             binding.executePendingBindings()
         }
    }
}
