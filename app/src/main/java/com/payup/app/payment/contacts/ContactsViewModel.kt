package com.payup.app.payment.contacts

import com.payup.di.FragmentScope
import com.payup.model.Contact
import io.reactivex.Observable
import javax.inject.Inject

@FragmentScope
class ContactsViewModel @Inject constructor() {
    fun contacts(): Observable<List<Contact>> {
        return Observable.just(
                listOf(
                        Contact("José Silva", "(11) 96225-3044"),
                        Contact("Maria do Carmo", "(11) 96225-3044"),
                        Contact("Carlos Eduardo", "(11) 96225-3044"),
                        Contact("José Silva", "(11) 96225-3044"),
                        Contact("José Silva", "(11) 96225-3044"),
                        Contact("José Silva", "(11) 96225-3044"),
                        Contact("José Silva", "(11) 96225-3044"),
                        Contact("José Silva", "(11) 96225-3044"),
                        Contact("José Silva", "(11) 96225-3044")
                )
        )
    }
}
