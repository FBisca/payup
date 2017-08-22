package com.payup.data.repository

import com.payup.data.datasource.ContactDataSource
import com.payup.data.datasource.UserDataSource
import com.payup.data.manager.TokenManager
import com.payup.data.network.NetworkApi
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
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
    fun test_dodie() {
        val test = repository.getUser().test()
        test.assertComplete()

        val user = test.values().first()
        assertNotNull(user)
        assertThat(user.name, equalTo("Dodie Clark"))
        assertThat(user.email, equalTo("doddieoddle@gmail.com"))
    }

}
