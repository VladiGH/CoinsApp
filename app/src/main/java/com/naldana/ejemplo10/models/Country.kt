package com.naldana.ejemplo10.models


data class Country (
    var _id: String? = null,
    val name: String = "N/A",
    val coins: ArrayList<String> = ArrayList()
)