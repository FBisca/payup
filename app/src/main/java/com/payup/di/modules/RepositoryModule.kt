package com.payup.di.modules

import com.payup.data.repository.ContactRepository
import com.payup.data.repository.ContactRepositoryImpl
import com.payup.data.repository.UserRepository
import com.payup.data.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
@Singleton
abstract class RepositoryModule {
    @Binds
    abstract fun providesUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun providesContactRepository(userRepositoryImpl: ContactRepositoryImpl): ContactRepository
}
