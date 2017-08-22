package com.payup.data.network.entities

import com.squareup.moshi.Json

data class SendMoneyRequestRaw(
        val token: String,
        @Json(name = "valor") val value: Double,
        @Json(name = "ClienteId") val clientId: String
)
