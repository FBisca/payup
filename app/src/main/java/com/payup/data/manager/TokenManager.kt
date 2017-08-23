package com.payup.data.manager

import com.payup.data.OpenForTests
import com.payup.data.datasource.UserDataSource
import com.payup.data.network.NetworkApi
import io.reactivex.Observable
import java.net.URLEncoder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenForTests
class TokenManager @Inject constructor(
        private val userDataSource: UserDataSource,
        private val networkApi: NetworkApi
) {

    private var tokenEmitter: Observable<String>? = null

    private fun requestToken(): Observable<String> {
        return userDataSource.user()
                .flatMap { networkApi.generateToken(it.name, it.email) }
                .toObservable()
                .replay(1)
                .autoConnect()
    }

    fun getToken(): Observable<String> {
        val emitter = tokenEmitter?.onErrorResumeNext { _: Throwable -> renewToken() }
        return emitter ?: renewToken()
    }

    private fun renewToken(): Observable<String> {
        return requestToken().apply { tokenEmitter = this }
    }

}
