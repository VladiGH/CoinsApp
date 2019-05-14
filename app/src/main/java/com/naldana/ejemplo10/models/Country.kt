package com.naldana.ejemplo10.models

import android.os.Parcel
import android.os.Parcelable


data class Country (
    var _id: String? = null,
    var name: String = "N/A"
):Parcelable{
    constructor(parcel: Parcel): this (
        parcel.readString(),
        parcel.readString()
    )

    constructor(nothing: Nothing?, toLowerCase: String, toLowerCase1: String) : this()

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Country> {
        override fun createFromParcel(parcel: Parcel): Country {
            return Country(parcel)
        }

        override fun newArray(size: Int): Array<Country?> {
            return arrayOfNulls(size)
        }
    }
}