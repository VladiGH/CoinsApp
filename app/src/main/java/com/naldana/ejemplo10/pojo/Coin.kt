package com.naldana.ejemplo10.pojo

import android.os.Parcel
import android.os.Parcelable

data class Coin (
    val name:String = "N/A",
    val country:String = "N/A",
    val year: String = "N/A",
    val available:Boolean = true
    ): Parcelable {
    constructor(parcel: Parcel) : this(
        name = parcel.readString()!!,
        country = parcel.readString()!!,
        year = parcel.readString()!!,
            available = parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(country)
        parcel.writeString(year)
        parcel.writeByte(if (available) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField val CREATOR = object : Parcelable.Creator<Coin> {
            override fun createFromParcel(parcel: Parcel): Coin = Coin(parcel)
            override fun newArray(size: Int): Array<Coin?> = arrayOfNulls(size)
        }
    }

}