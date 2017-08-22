package com.payup.data.repository

import com.payup.data.datasource.ContactDataSource
import com.payup.data.datasource.UserDataSource
import com.payup.data.manager.TokenManager
import com.payup.data.network.NetworkApi
import com.payup.data.network.entities.GetTransferResponseRaw
import com.payup.data.network.entities.SendMoneyRequestRaw
import com.payup.model.Contact
import com.payup.model.Transaction
import com.payup.model.User
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
        private val userDataSource: UserDataSource,
        private val contactDataSource: ContactDataSource,
        private val tokenManager: TokenManager,
        private val networkApi: NetworkApi,
        private val locale: Locale
) : UserRepository {

    override fun getUser(): Single<User> {
        return userDataSource.user()
    }

    override fun sendPayment(value: Double, contact: Contact): Completable {
        return tokenManager.getToken()
                .flatMapSingle { networkApi.sendMoney(SendMoneyRequestRaw(it, value, contact.clientId.toString())) }
                .flatMapCompletable {
                    when (it) {
                        true -> Completable.complete()
                        else -> Completable.error(IOException("Could not send payment"))
                    }
                }
    }

    override fun getTransactionHistory(): Single<List<Transaction>> {
        return tokenManager.getToken().firstOrError()
                .flatMap { networkApi.getTransfers(it) }
                // Type Inference is not working for RxJava2
                .zipWith(contactDataSource.contacts(), BiFunction<List<GetTransferResponseRaw>, List<Contact>, List<Transaction>> { transfers, contacts ->
                    transfers.map { each -> each to contacts.find { it.clientId == each.clientId } }
                            .filter { it.second != null }
                            .map { Transaction(it.second as Contact, it.first.value, parseDate(it.first.date)) }
                })
    }

    private fun parseDate(date: String): Date {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", locale)
        return formatter.parse(date)
    }
}
