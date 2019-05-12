package com.naldana.ejemplo10.models

import android.os.Parcel
import android.os.Parcelable

data class Coin(
    var _id : String? = null,
    var name: String = "N/A",
    var country: String = "N/A",
    var year: Int = 0,
    var available: Boolean = false
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(name)
        parcel.writeString(country)
        parcel.writeInt(year)
        parcel.writeByte(if (available) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Coin> {
        override fun createFromParcel(parcel: Parcel): Coin {
            return Coin(parcel)
        }

        override fun newArray(size: Int): Array<Coin?> {
            return arrayOfNulls(size)
        }
    }
}