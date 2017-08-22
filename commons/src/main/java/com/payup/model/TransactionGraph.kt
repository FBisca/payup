package com.payup.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class TransactionGraph(
    val user: Contact,
    val totalValue: Double,
    val percentage: Int
): Parcelable
