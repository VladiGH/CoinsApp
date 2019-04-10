package com.naldana.ejemplo10.pojo

import android.os.Parcel
import android.os.Parcelable

data class Coin (
    val name:String = "N/A",
    val country:String = "N/A",
    val ano: String = "N/A",
    val avaliable:Boolean = true
    ): Parcelable {
    constructor(parcel: Parcel) : this(
        name = parcel.readString(),
        country = parcel.readString(),
        ano = parcel.readString(),
        avaliable = parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(country)
        parcel.writeString(ano)
        parcel.writeByte(if (avaliable) 1 else 0)
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