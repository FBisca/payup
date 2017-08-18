package com.payup.app.payment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.payup.R
import com.payup.model.Contact
import io.reactivex.disposables.CompositeDisposable

class PaymentContactsFragment : Fragment() {

    private val viewModel: PaymentContactsViewModel = PaymentContactsViewModel()

    private lateinit var contactList: RecyclerView
    private val adapter = PaymentContactsAdapter()
    private val disposables = CompositeDisposable()

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
        contactList.layoutManager = LinearLayoutManager(context)
        contactList.adapter = adapter
        contactList.addItemDecoration(PaymentContactsItemDecoration(context))

        viewModel.contacts()
                .subscribe(
                        { updateList(it) }
                )
                .apply { disposables.add(this) }
    }

    private fun updateList(newList: List<Contact>) {
        val oldList = adapter.items
        adapter.items = newList

        DiffUtil.calculateDiff(PaymentContactsDiffCalback(oldList, newList))
                .dispatchUpdatesTo(adapter)

        contactList.scrollToPosition(0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }
}
