package com.payup.app.payment

import com.payup.di.ActivityScope
import com.payup.model.Contact
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@ActivityScope
class PaymentViewModel @Inject constructor() : PaymentContactsAdapter.Listener {

    val contactClickEvents: PublishSubject<Contact> = PublishSubject.create()

    override fun onContactClicked(contact: Contact) {
        contactClickEvents.onNext(contact)
    }
}
