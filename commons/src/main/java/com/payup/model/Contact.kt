package com.payup.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class Contact(
        val name: String,
        val phoneNumber: String
) : Parcelable
