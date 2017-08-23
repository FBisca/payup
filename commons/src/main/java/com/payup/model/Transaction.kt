package com.payup.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

// Could not user @Parcelize in this class, cause it still experimental and it not working correctly with Date type.
data class Transaction(
        val user: Contact,
        val value: Double,
        val date: Date
): Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readParcelable(Contact::class.java.classLoader),
            parcel.readDouble(),
            Date(parcel.readLong())
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(user, flags)
        parcel.writeDouble(value)
        parcel.writeLong(date.time)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Transaction> {
        override fun createFromParcel(parcel: Parcel): Transaction {
            return Transaction(parcel)
        }

        override fun newArray(size: Int): Array<Transaction?> {
            return arrayOfNulls(size)
        }
    }

}
