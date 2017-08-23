package com.payup.app.ui.screens.payment.valueInput

import com.payup.app.Navigator
import com.payup.model.Fabricator
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ValueInputViewModelTest {

    lateinit var viewModel: ValueInputViewModel

    @Mock
    lateinit var navigator: Navigator

    val contact = Fabricator.contact()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = ValueInputViewModel(contact, navigator)
    }

    @Test
    fun test_testInitialValueIsZero() {
        val test = viewModel.inputEvents.test()
        assertThat(test.values().last(), equalTo("0"))
    }

    @Test
    fun test_inputValuesOneToFive() {
        val test = viewModel.inputEvents.test()

        viewModel.inputClick(1)
        assertThat(test.values().last(), equalTo("1"))

        viewModel.inputClick(2)
        assertThat(test.values().last(), equalTo("12"))

        viewModel.inputClick(3)
        assertThat(test.values().last(), equalTo("123"))

        viewModel.inputClick(4)
        assertThat(test.values().last(), equalTo("1234"))

        viewModel.inputClick(5)
        assertThat(test.values().last(), equalTo("12345"))
    }

    @Test
    fun test_inputValuesSixToZero() {
        val test = viewModel.inputEvents.test()

        viewModel.inputClick(6)
        assertThat(test.values().last(), equalTo("6"))

        viewModel.inputClick(7)
        assertThat(test.values().last(), equalTo("67"))

        viewModel.inputClick(8)
        assertThat(test.values().last(), equalTo("678"))

        viewModel.inputClick(9)
        assertThat(test.values().last(), equalTo("6789"))

        viewModel.inputClick(0)
        assertThat(test.values().last(), equalTo("67890"))
    }

    @Test
    fun test_negativeOneShouldDelete() {
        val test = viewModel.inputEvents.test()
        viewModel.inputClick(1)
        viewModel.inputClick(1)

        viewModel.inputClick(-1)

        assertThat(test.values().last(), equalTo("1"))
    }

    @Test
    fun test_backspaceBeyondZeroShouldAlwaysReturnZero() {
        val test = viewModel.inputEvents.test()
        viewModel.inputClick(1)

        viewModel.inputClick(-1)
        viewModel.inputClick(-1)
        viewModel.inputClick(-1)

        assertThat(test.values().last(), equalTo("0"))
    }

    @Test
    fun test_onlyZerosShouldNotConcat() {
        val test = viewModel.inputEvents.test()
        viewModel.inputClick(0)
        viewModel.inputClick(0)
        viewModel.inputClick(0)

        assertThat(test.values().last(), equalTo("0"))
    }

    @Test
    fun test_firstNumberShouldOverrideZero() {
        val test = viewModel.inputEvents.test()
        viewModel.inputClick(1)

        assertThat(test.values().last(), equalTo("1"))
    }

    @Test
    fun test_maxLengthShouldOverride() {
        val test = viewModel.inputEvents.test()
        viewModel.inputClick(2)
        viewModel.inputClick(1)
        viewModel.inputClick(1)
        viewModel.inputClick(1)
        viewModel.inputClick(1)
        viewModel.inputClick(1)
        viewModel.inputClick(1)
        viewModel.inputClick(1)

        assertThat(test.values().last(), equalTo("1111111"))
    }

}
