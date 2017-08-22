package com.payup.data.repository

import com.payup.data.datasource.ContactDataSource
import com.payup.model.Contact
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactRepositoryImpl @Inject constructor(
        private val contactDataSource: ContactDataSource
) : ContactRepository {
    override fun getContacts(): Single<List<Contact>> {
        return contactDataSource.contacts()
    }
}
