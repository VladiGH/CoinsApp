package com.naldana.ejemplo10.utils.networking.interfaces

import retrofit2.Call
import retrofit2.http.GET
import com.naldana.ejemplo10.utils.ServerInfo

interface CurrencyInterface {

    @GET(ServerInfo.monedaUrl)
    fun requestCurrencies(): Call<String>

}