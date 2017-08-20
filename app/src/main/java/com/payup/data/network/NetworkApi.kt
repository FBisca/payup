package com.payup.data.network

import com.payup.data.network.entities.GetTransferContactRaw
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface NetworkApi {

    @GET("GenerateToken")
    fun generateToken(@Query("name") name: String, @Query("email") email: String): Single<String>

    @POST("SendMoney")
    fun sendMoney(
            @Query("ClienteId") clientId: String,
            @Query("token") token: String,
            @Query("valor") value: Double
    ): Single<Boolean>

    @GET("GetTransfers")
    fun getTransfers(@Query("token") token: String): Single<List<GetTransferContactRaw>>
}
