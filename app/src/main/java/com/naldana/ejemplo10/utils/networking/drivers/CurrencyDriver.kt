package com.naldana.ejemplo10.utils.networking.drivers

import android.util.Log
import com.naldana.ejemplo10.pojo.Coin
import com.naldana.ejemplo10.utils.ServerInfo
import com.naldana.ejemplo10.utils.converter.CurrencyConverter
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import com.naldana.ejemplo10.utils.networking.interfaces.CurrencyInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrencyDriver {

    private val tag = "CurrencyDriver"

    /**
     * ScalarsConverterFactory its just code that will automatically parse
     * the http response into a Kotlin String
     * */
    private var httpAdapter = Retrofit.Builder()
            .baseUrl(ServerInfo.baseURL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()


    fun getCurrencies(afterMetod: (ArrayList<Coin>) -> Unit){
        httpAdapter.create(CurrencyInterface::class.java).requestCurrencies().enqueue(
                object : Callback<String> {

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        t.printStackTrace()
                        Log.i(tag, "La conexion fallo o algo")
                    }

                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        when (response.code()) {
                            200 -> {
                                Log.d(tag, "Repose: " + response.body())
                                afterMetod(CurrencyConverter().getCurrencyList(response.body()?: "{}"))
                            }

                            else -> {
                                val unexpected = response.code().toString() + response.message().toString()
                                Log.e(tag, unexpected)
                            }
                        }
                    }
                }
        )
    }
}