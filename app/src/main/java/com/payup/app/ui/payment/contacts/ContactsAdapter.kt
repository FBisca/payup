package com.payup.app.ui.payment.contacts

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.payup.databinding.ListContactBinding
import com.payup.model.Contact

class ContactsAdapter : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    var items = emptyList<Contact>()
    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = ListContactBinding.inflate(inflater, parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(
            private val binding: ListContactBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener { listener?.onContactClicked(items[adapterPosition]) }
        }

         fun bind(contact: Contact) {
             binding.contact = contact
             binding.executePendingBindings()
         }
    }

    interface Listener {
        fun onContactClicked(contact: Contact)
    }
}
