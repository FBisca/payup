package com.payup.app.ui.payment.contacts

import com.payup.data.repository.ContactRepository
import com.payup.di.FragmentScope
import com.payup.model.Contact
import io.reactivex.Observable
import javax.inject.Inject

@FragmentScope
class ContactsViewModel @Inject constructor(
        private val contactRepository: ContactRepository
) {
    fun contacts(): Observable<List<Contact>> {
        return contactRepository.getContacts().toObservable()
    }
}
