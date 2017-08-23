package com.payup.app.ui.screens.payment.contacts

import com.payup.data.manager.TestSchedulerManager
import com.payup.data.repository.ContactRepository
import com.payup.test.Fabricator
import io.reactivex.Single
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class ContactsViewModelTest {

    lateinit var viewModel: ContactsViewModel

    @Mock
    lateinit var contactRepository: ContactRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = ContactsViewModel(contactRepository, TestSchedulerManager())
    }

    @Test
    fun test_verifyContactIsCorrect() {
        val list = listOf(Fabricator.contact())

        `when`(contactRepository.getContacts()).thenReturn(Single.just(list))

        val test = viewModel.contacts().test()
        test.assertNoErrors()

        verify(contactRepository).getContacts()
        assertThat(test.values().single(), `is`(list))
    }
}
