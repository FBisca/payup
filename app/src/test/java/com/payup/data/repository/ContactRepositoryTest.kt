package com.payup.data.repository

import com.squareup.moshi.Moshi
import dagger.Lazy
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.FileInputStream
import java.io.InputStream
import javax.inject.Provider

class ContactRepositoryTest {

    private lateinit var repository: ContactRepositoryImpl

    private val contactJsonLazy = object : Provider<Lazy<InputStream>> {
        override fun get(): Lazy<InputStream> {
            return Lazy {
                FileInputStream(javaClass.classLoader.getResource("json/contacts.json").path)
            }
        }
    }

    @Before
    fun setUp() {
        repository = ContactRepositoryImpl(contactJsonLazy, Moshi.Builder().build())
    }

    @Test
    fun test_contactJsonParsing() {
        val test = repository.getContacts().test()
        test.assertComplete()

        val list = test.values().first()
        assertNotNull(list)
        assertTrue(list.isNotEmpty())
    }

}
