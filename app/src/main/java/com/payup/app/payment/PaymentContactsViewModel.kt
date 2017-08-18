package com.payup.app.payment

import com.payup.model.Contact
import io.reactivex.Observable

class PaymentContactsViewModel {

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
