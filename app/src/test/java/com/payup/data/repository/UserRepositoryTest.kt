package com.payup.data.repository

import com.payup.test.anyNonNull
import com.payup.data.datasource.ContactDataSource
import com.payup.data.datasource.UserDataSource
import com.payup.data.manager.TokenManager
import com.payup.data.network.NetworkApi
import com.payup.test.Fabricator
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.io.IOException
import java.util.*

class UserRepositoryTest {

    private lateinit var repository: UserRepositoryImpl

    @Mock
    lateinit var userDataSource: UserDataSource

    @Mock
    lateinit var contactDataSource: ContactDataSource

    @Mock
    lateinit var networkApi: NetworkApi

    @Mock
    lateinit var tokenManager: TokenManager

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = UserRepositoryImpl(userDataSource, contactDataSource, tokenManager, networkApi, Locale.US)
    }

    @Test
    fun test_user() {
        `when`(userDataSource.user()).thenReturn(Single.just(Fabricator.user()))
        val test = repository.getUser().test()
        test.assertComplete()

        val user = test.values().first()
        assertNotNull(user)
    }

    @Test
    fun test_sendPayment() {
        val contact = Fabricator.contact()
        val value = 10.0

        `when`(tokenManager.getToken()).thenReturn(Observable.just(""))
        `when`(networkApi.sendMoney(anyNonNull())).thenReturn(Single.just(true))

        val test = repository.sendPayment(value, contact).test()
        test.assertComplete()
    }

    @Test
    fun test_sendPaymentFailure() {
        val contact = Fabricator.contact()
        val value = 10.0

        `when`(tokenManager.getToken()).thenReturn(Observable.just(""))
        `when`(networkApi.sendMoney(anyNonNull())).thenReturn(Single.just(false))

        val test = repository.sendPayment(value, contact).test()
        test.assertError(IOException::class.java)
    }

}
