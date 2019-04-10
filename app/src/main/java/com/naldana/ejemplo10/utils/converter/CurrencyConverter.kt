package com.naldana.ejemplo10.utils.converter

import android.util.Log
import com.naldana.ejemplo10.pojo.Coin
import org.json.JSONObject
import com.google.gson.Gson

class CurrencyConverter {

    private val gson = Gson()
    private val tag = "CurrencyConverter"


    fun getCurrencyList(data: String):ArrayList<Coin>{
        val dataContainer = JSONObject(data)
        val dataList = arrayListOf<Coin>()
        dataContainer.keys().forEach {
            Log.i(tag, it)
            dataList.add(gson.fromJson(dataContainer.get(it).toString(), Coin::class.java))
        }
        return  dataList
    }

}