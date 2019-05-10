package com.naldana.ejemplo10.database

import android.content.Context
import com.naldana.ejemplo10.models.Coin
import com.naldana.ejemplo10.models.Country

class DataProvider(appContext: Context) {

    private val dataProvider = RealTimeDatabase()
    private val dbHelper = DatabaseSQL(appContext)
    private val coinList = ArrayList<Coin>()
    private val countryList = ArrayList<Country>()


    fun closeDb(){
        dbHelper.close()
    }
}