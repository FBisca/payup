package com.payup.data.manager

import com.payup.data.datasource.UserDataSource
import com.payup.data.network.NetworkApi
import com.payup.model.Fabricator
import io.reactivex.Single
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.io.IOException

class TokenManagerTest {

    lateinit var manager : TokenManager

    @Mock
    lateinit var userDataSource: UserDataSource

    @Mock
    lateinit var networkApi: NetworkApi

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        manager = TokenManager(userDataSource, networkApi)
    }

    @Test
    fun test_tokenCallTwiceShouldRequestJustOnce() {
        val user = Fabricator.user()
        `when`(userDataSource.user()).thenReturn(Single.just(user))
        `when`(networkApi.generateToken(user.name, user.email)).thenReturn(Single.just("token"))

        val first = manager.getToken().test()
        first.assertNoErrors()
        assertThat(first.valueCount(), equalTo(1))
        assertThat(first.values().first(), equalTo("token"))

        val second = manager.getToken().test()
        second.assertNoErrors()
        assertThat(second.valueCount(), equalTo(1))
        assertThat(second.values().first(), equalTo("token"))

        verify(networkApi, times(1)).generateToken(user.name, user.email)
    }

    @Test
    fun test_onErrorTokenMustBeReviewed() {
        val user = Fabricator.user()
        `when`(userDataSource.user()).thenReturn(Single.just(user))
        `when`(networkApi.generateToken(user.name, user.email)).thenReturn(Single.error(IOException()))

        val first = manager.getToken().test()
        first.assertError(IOException::class.java)

        `when`(networkApi.generateToken(user.name, user.email)).thenReturn(Single.just("token"))

        val second = manager.getToken().test()
        second.assertNoErrors()
        assertThat(second.valueCount(), equalTo(1))
        assertThat(second.values().first(), equalTo("token"))

        verify(networkApi, times(2)).generateToken(user.name, user.email)
    }

    @Test
    fun test_afterErrorTokenMustBeCached() {
        val user = Fabricator.user()
        `when`(userDataSource.user()).thenReturn(Single.just(user))
        `when`(networkApi.generateToken(user.name, user.email)).thenReturn(Single.error(IOException()))

        val first = manager.getToken().test()
        first.assertError(IOException::class.java)

        `when`(networkApi.generateToken(user.name, user.email)).thenReturn(Single.just("token"))

        val second = manager.getToken().test()
        second.assertNoErrors()

        val third = manager.getToken().test()
        third.assertNoErrors()

        verify(networkApi, times(2)).generateToken(user.name, user.email)
    }

    @Test
    fun test_consecutiveErrors() {
        val user = Fabricator.user()
        `when`(userDataSource.user()).thenReturn(Single.just(user))
        `when`(networkApi.generateToken(user.name, user.email)).thenReturn(Single.error(IOException()))

        val first = manager.getToken().test()
        first.assertError(IOException::class.java)

        val second = manager.getToken().test()
        second.assertError(IOException::class.java)

        val third = manager.getToken().test()
        third.assertError(IOException::class.java)

        verify(networkApi, times(3)).generateToken(user.name, user.email)
    }
}
