package com.payup.data.network

import com.payup.data.network.entities.GetTransferResponseRaw
import com.payup.data.network.entities.SendMoneyRequestRaw
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface NetworkApi {

    @GET("GenerateToken")
    fun generateToken(@Query("nome") name: String, @Query("email") email: String): Single<String>

    @POST("SendMoney")
    fun sendMoney(@Body payload: SendMoneyRequestRaw): Single<Boolean>

    @GET("GetTransfers")
    fun /**/getTransfers(@Query("token") token: String): Single<List<GetTransferResponseRaw>>
}
