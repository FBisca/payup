package com.payup.data.repository

import com.payup.model.User
import io.reactivex.Single

interface UserRepository {
    fun getUser(): Single<User>
}
