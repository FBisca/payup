package com.payup.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class Contact(
        val clientId: Int,
        val name: String,
        val phoneNumber: String,
        val imageUrl: String
) : Parcelable
