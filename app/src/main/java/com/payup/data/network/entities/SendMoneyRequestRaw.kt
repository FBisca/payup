package com.payup.data.network.entities

data class SendMoneyRequestRaw(
        val ClienteId : String,
        val token: String,
        val valor: Double
)
