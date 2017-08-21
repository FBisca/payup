package com.payup.data.datasource

import com.payup.data.OpenForTests
import com.payup.model.User
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenForTests
class UserDataSource @Inject constructor() {
    fun user(): Single<User> {
        return Single.just(User("Dodie Clark", "doddieoddle@gmail.com"))
    }
}
