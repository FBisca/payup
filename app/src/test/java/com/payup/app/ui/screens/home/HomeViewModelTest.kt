package com.payup.app.ui.screens.home

import com.payup.app.Navigator
import com.payup.data.manager.TestSchedulerManager
import com.payup.data.manager.TokenManager
import com.payup.data.repository.UserRepository
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class HomeViewModelTest {

    lateinit var viewModel: HomeViewModel

    @Mock
    lateinit var navigator: Navigator

    @Mock
    lateinit var tokenManager: TokenManager

    @Mock
    lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = HomeViewModel(navigator, tokenManager, TestSchedulerManager(), userRepository)
    }

    @Test
    fun test_verifyIfTokenIsWarmedUpOnViewCreate() {
        val token = "token"
        `when`(tokenManager.getToken()).thenReturn(Observable.just(token))
        viewModel.viewCreated()

        verify(tokenManager).getToken()
    }

    @Test
    fun test_verifySendPaymentNavigatesCorrectly() {
        viewModel.sendPayment()

        verify(navigator).goToPaymentContacts()
    }

    @Test
    fun test_verifyHistoryNavigatesCorrectly() {
        viewModel.history()

        verify(navigator).goToHistoryActivity()
    }
}
