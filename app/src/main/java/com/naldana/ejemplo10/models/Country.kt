package com.naldana.ejemplo10.models

import android.os.Parcel
import android.os.Parcelable


data class Country (
    var _id: String? = null,
    val name: String = "N/A",
    val coins: ArrayList<String> = ArrayList()
)