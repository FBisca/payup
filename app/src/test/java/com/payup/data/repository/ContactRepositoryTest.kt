package com.payup.data.repository

import com.payup.data.datasource.ContactDataSource
import com.payup.test.Fabricator
import io.reactivex.Single
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class ContactRepositoryTest {

    private lateinit var repository: ContactRepositoryImpl

    @Mock
    lateinit var contactDataSource: ContactDataSource

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = ContactRepositoryImpl(contactDataSource)
    }

    @Test
    fun test_dataSource() {
        val list = listOf(Fabricator.contact())

        `when`(contactDataSource.contacts()).thenReturn(Single.just(list))
        val test = repository.getContacts().test()
        test.assertComplete()

        assertThat(test.values().last(), `is`(list))
    }

}
