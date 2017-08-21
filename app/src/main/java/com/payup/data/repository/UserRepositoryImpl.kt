package com.payup.data.repository

import com.payup.data.datasource.UserDataSource
import com.payup.data.manager.TokenManager
import com.payup.data.network.NetworkApi
import com.payup.model.Contact
import com.payup.model.User
import io.reactivex.Completable
import io.reactivex.Single
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
        private val userDataSource: UserDataSource,
        private val tokenManager: TokenManager,
        private val networkApi: NetworkApi
): UserRepository {

    override fun getUser(): Single<User> {
        return userDataSource.user()
    }

    override fun sendPayment(value: Double, contact: Contact): Completable {
        return tokenManager.getToken()
                .flatMapSingle { networkApi.sendMoney(contact.clientId, it, value) }
                .flatMapCompletable { when (it) {
                    true -> Completable.complete()
                    else -> Completable.error(IOException("Could not send payment"))
                } }
    }
}
