package com.payup.data.datasource

import com.squareup.moshi.Moshi
import dagger.Lazy
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.FileInputStream
import java.io.InputStream
import javax.inject.Provider

class ContactDataSourceTest {

    private lateinit var source: ContactDataSource

    private val contactJsonLazy = object : Provider<Lazy<InputStream>> {
        override fun get(): Lazy<InputStream> {
            return Lazy {
                FileInputStream(javaClass.classLoader.getResource("json/contacts.json").path)
            }
        }
    }

    @Before
    fun setUp() {
        source = ContactDataSource(contactJsonLazy, Moshi.Builder().build())
    }

    @Test
    fun test_contactJsonParsing() {
        val test = source.contacts().test()
        test.assertComplete()

        val list = test.values().first()
        assertNotNull(list)
        assertTrue(list.isNotEmpty())
    }

}
