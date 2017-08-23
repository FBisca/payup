package com.payup.app.ui.screens.payment

import com.payup.app.Navigator
import com.payup.data.manager.TestSchedulerManager
import com.payup.data.repository.UserRepository
import com.payup.model.Fabricator
import io.reactivex.Completable
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class ConfirmationViewModelTest {

    lateinit var viewModel: ConfirmationViewModel

    @Mock
    lateinit var navigator: Navigator

    @Mock
    lateinit var userRepository: UserRepository

    private val contact = Fabricator.contact()
    private val value = 100.0

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = ConfirmationViewModel(contact, value, navigator, userRepository, TestSchedulerManager())
    }

    @Test
    fun test_confirmShouldSendTheRightParameters() {
        `when`(userRepository.sendPayment(value, contact)).thenReturn(Completable.complete())
        viewModel.confirmClick()

        verify(userRepository).sendPayment(value, contact)
    }

    @Test
    fun test_testViewStateSuccess() {
        val test = viewModel.viewState.test()

        `when`(userRepository.sendPayment(value, contact)).thenReturn(Completable.complete())
        viewModel.confirmClick()

        assertThat(test.values().last(), `is`(instanceOf(ConfirmationViewModel.ViewState.Success::class.java)))
    }

    @Test
    fun test_testViewStateError() {
        val test = viewModel.viewState.test()

        `when`(userRepository.sendPayment(value, contact)).thenReturn(Completable.error(IllegalArgumentException()))
        viewModel.confirmClick()

        assertThat(test.values().last(), `is`(instanceOf(ConfirmationViewModel.ViewState.Error::class.java)))
    }

    @Test
    fun test_testViewStateLoading() {
        val test = viewModel.viewState.test()

        `when`(userRepository.sendPayment(value, contact)).thenReturn(Completable.complete())
        viewModel.confirmClick()

        test.assertValueAt(1, ConfirmationViewModel.ViewState.Loading)
    }

    @Test
    fun test_testConfirmButtonWhenStateIsSuccess() {
        viewModel.viewState.onNext(ConfirmationViewModel.ViewState.Success)
        viewModel.confirmClick()

        verify(navigator).returnToHome()
    }

}
