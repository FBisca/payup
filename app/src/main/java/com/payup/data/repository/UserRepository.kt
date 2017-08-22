package com.payup.data.repository

import com.payup.model.Contact
import com.payup.model.Transaction
import com.payup.model.User
import io.reactivex.Completable
import io.reactivex.Single

interface UserRepository {
    fun getUser(): Single<User>
    fun sendPayment(value: Double, contact: Contact): Completable
    fun getTransactionHistory(): Single<List<Transaction>>
}
