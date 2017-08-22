package com.payup.data.network.entities

import com.squareup.moshi.Json

data class GetTransferResponseRaw(
        @Json(name = "Id") val id: Int,
        @Json(name = "Data") val date: String,
        @Json(name = "Valor") val value: Double,
        @Json(name = "Token") val token: String,
        @Json(name = "ClienteId") val clientId: Int
)
