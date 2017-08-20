package com.payup.data.network.entities

data class GetTransferContactRaw(
        val Id: Int,
        val ClienteId: Int,
        val Valor: Double,
        val Token: String,
        val Data: String
)
