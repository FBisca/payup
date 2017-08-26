package com.payup.app.ui.screens.payment.contacts

import com.payup.data.manager.SchedulerManager
import com.payup.data.repository.ContactRepository
import com.payup.di.arch.FragmentScope
import com.payup.model.Contact
import io.reactivex.Observable
import javax.inject.Inject

@FragmentScope
class ContactsViewModel @Inject constructor(
        private val contactRepository: ContactRepository,
        private val schedulerManager: SchedulerManager

) {
    fun contacts(): Observable<List<Contact>> {
        return contactRepository.getContacts().toObservable()
                .subscribeOn(schedulerManager.workThread())
                .observeOn(schedulerManager.mainThread())
    }
}
