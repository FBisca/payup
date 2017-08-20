package com.payup.data.repository

import com.payup.model.User
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(): UserRepository {
    override fun getUser(): Single<User> {
        return Single.just(User("Dodie Clark", "doddieoddle@gmail.com"))
    }
}
