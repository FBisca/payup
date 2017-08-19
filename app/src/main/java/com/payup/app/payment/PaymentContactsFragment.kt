package com.payup.app.payment

import android.os.Bundle
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.payup.R
import com.payup.app.components.ComponentFragment
import com.payup.di.components.PaymentContactsFragmentComponent
import com.payup.model.Contact
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class PaymentContactsFragment : ComponentFragment() {

    @Inject
    lateinit var viewModel: PaymentContactsViewModel

    @Inject
    lateinit var adapterListener: PaymentContactsAdapter.Listener

    private lateinit var contactList: RecyclerView
    private val contactAdapter = PaymentContactsAdapter()
    private val disposables = CompositeDisposable()

    override fun initInjection() {
        injectionBuilder<PaymentContactsFragmentComponent.Builder>()
                .build()
                .injectMembers(this)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_payment_contacts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contactList = view.findViewById(R.id.contacts_list)
        contactAdapter.listener = adapterListener

        contactList.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(PaymentContactsItemDecoration(context))
            adapter = contactAdapter
        }

        bindContacts()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        contactAdapter.listener = null
        disposables.clear()
    }

    private fun bindContacts() {
        viewModel.contacts()
                .subscribe(
                        { updateList(it) }
                )
                .apply { disposables.add(this) }
    }

    private fun updateList(newList: List<Contact>) {
        val oldList = contactAdapter.items
        contactAdapter.items = newList

        DiffUtil.calculateDiff(PaymentContactsDiffCalback(oldList, newList))
                .dispatchUpdatesTo(contactAdapter)
    }
}
