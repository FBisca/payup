package com.payup.data.repository

import com.payup.model.Contact
import io.reactivex.Single

interface ContactRepository {
    fun getContacts(): Single<List<Contact>>
}
