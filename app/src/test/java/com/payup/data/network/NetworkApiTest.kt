package com.payup.data.network

import com.payup.data.network.entities.SendMoneyRequestRaw
import com.payup.test.Fabricator
import okhttp3.HttpUrl

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.skyscreamer.jsonassert.JSONAssert
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.FileInputStream

class NetworkApiTest {

    lateinit var networkApi: NetworkApi

    lateinit var mockServer: MockWebServer

    @Before
    fun setUp() {
        mockServer = MockWebServer()
        mockServer.start()

        networkApi = Retrofit.Builder()
                .baseUrl(mockServer.url("/"))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(NetworkApi::class.java)
    }

    @After
    fun shutdown() {
        mockServer.shutdown()
    }

    @Test
    fun test_generateToken() {
        val user = Fabricator.user()
        val token = "31e14b03-a650-49a7-b992-95b160819846"

        mockServer.enqueue(MockResponse().setBody("\"$token\""))

        val test = networkApi.generateToken(user.name, user.email).test()
        test.assertNoErrors()

        assertThat(test.values().last(), equalTo(token))

        val request = mockServer.takeRequest()

        val expected = HttpUrl.Builder()
                .scheme("http")
                .host(mockServer.hostName)
                .port(mockServer.port)
                .addPathSegment("GenerateToken")
                .addQueryParameter("nome", user.name)
                .addQueryParameter("email", user.email)
                .build()

        assertThat(request.requestUrl, equalTo(expected))
    }

    @Test
    fun test_sendMoney() {
        val token = "31e14b03-a650-49a7-b992-95b160819846"
        val value = 100.0
        val clientId = 1

        mockServer.enqueue(MockResponse().setBody("true"))

        val test = networkApi.sendMoney(SendMoneyRequestRaw(token, value, clientId.toString())).test()
        test.assertNoErrors()

        val request = mockServer.takeRequest()

        val expected = "{ClienteId:\"$clientId\",token:$token,valor:$value}"

        JSONAssert.assertEquals(expected, request.body.readUtf8(), false)
    }

    @Test
    fun test_getTransfers() {
        val token = "31e14b03-a650-49a7-b992-95b160819846"

        val responseFile = FileInputStream(javaClass.classLoader.getResource("json/getTransferResponse.json").path)
        val buffer = Okio.buffer(Okio.source(responseFile))
        val response = buffer.readUtf8()
        buffer.close()

        mockServer.enqueue(MockResponse().setBody(response))
        val test = networkApi.getTransfers(token).test()
        test.assertNoErrors()

        val request = mockServer.takeRequest()

        val expected = HttpUrl.Builder()
                .scheme("http")
                .host(mockServer.hostName)
                .port(mockServer.port)
                .addPathSegment("GetTransfers")
                .addQueryParameter("token", token)
                .build()

        assertThat(request.requestUrl, equalTo(expected))
        assertThat(test.values().last().size, equalTo(3))
    }
}
