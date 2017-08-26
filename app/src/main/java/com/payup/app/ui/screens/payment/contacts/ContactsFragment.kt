package com.payup.app.ui.screens.payment.contacts

import android.os.Bundle
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.payup.R
import com.payup.app.arch.ComponentFragment
import com.payup.model.Contact
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ContactsFragment : ComponentFragment() {

    @Inject
    lateinit var viewModel: ContactsViewModel

    @Inject
    lateinit var adapterListener: ContactsAdapter.Listener

    private lateinit var contactList: RecyclerView
    private val contactAdapter = ContactsAdapter()
    private val disposables = CompositeDisposable()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contactList = view.findViewById(R.id.contacts_list)

        contactList.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(ContactsItemDecoration(context))
            adapter = contactAdapter
        }

        contactAdapter.listener = adapterListener
        bindContacts()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        contactAdapter.listener = null
        disposables.clear()
    }

    private fun bindContacts() {
        viewModel.contacts()
                .subscribe { updateList(it) }
                .apply { disposables.add(this) }
    }

    private fun updateList(newList: List<Contact>) {
        val oldList = contactAdapter.items
        contactAdapter.items = newList

        DiffUtil.calculateDiff(ContactsDiffCalback(oldList, newList))
                .dispatchUpdatesTo(contactAdapter)
    }
}
