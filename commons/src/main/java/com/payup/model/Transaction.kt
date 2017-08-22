package com.payup.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@SuppressLint("ParcelCreator")
@Parcelize
data class Transaction(
        val user: Contact,
        val value: Double,
        val date: Date
): Parcelable
