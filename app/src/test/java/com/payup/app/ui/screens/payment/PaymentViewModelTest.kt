package com.payup.app.ui.screens.payment

import com.payup.app.Navigator
import com.payup.data.manager.TokenManager
import com.payup.data.repository.UserRepository
import com.payup.model.Fabricator
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.*
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class PaymentViewModelTest {

    lateinit var viewModel: PaymentViewModel

    @Mock
    lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = PaymentViewModel(PaymentViewModel.ViewState.ContactSelect, userRepository)
    }

    @Test
    fun test_stateChanges() {
        val test = viewModel.viewState.test()
        assertThat(test.values().last(), `is`(instanceOf(PaymentViewModel.ViewState.ContactSelect::class.java)))

        val contact = Fabricator.contact()
        viewModel.onContactClicked(contact)

        assertThat(test.values().last(), `is`(instanceOf(PaymentViewModel.ViewState.ValueInput::class.java)))

        val valueInput = test.values().last() as PaymentViewModel.ViewState.ValueInput
        assertThat(valueInput.contact, equalTo(contact))

        viewModel.onBackPressed()
        assertThat(test.values().last(), `is`(instanceOf(PaymentViewModel.ViewState.ContactSelect::class.java)))
    }

    @Test
    fun test_whenStateIsInitialBackPressedIsNotIntercepted() {
        assertThat(viewModel.onBackPressed(), `is`(false))
    }
}
