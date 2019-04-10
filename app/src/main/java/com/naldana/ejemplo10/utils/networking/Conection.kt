package com.naldana.ejemplo10.utils.networking

import com.naldana.ejemplo10.utils.networking.drivers.CurrencyDriver
class Conection {

    companion object {

        val myConector = CurrencyDriver()

        fun getdata(message: String, afterMetod: (m: String) -> Unit){
            afterMetod(message)
        }
    }
}