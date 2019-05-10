package com.naldana.ejemplo10.models

data class Coin(
    var _id : String? = null,
    var name: String = "N/A",
    var country: String = "N/A",
    var year: Int = 0,
    var available: Boolean = false
)