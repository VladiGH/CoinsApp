package com.naldana.ejemplo10.database

import android.content.Context
import com.naldana.ejemplo10.models.Coin
import com.naldana.ejemplo10.models.Country


class DataProvider(appContext: Context) {

    private val tag = this@DataProvider::class.java.simpleName
    private val realtimeData = RealTimeDatabase()
    private val localData = LocalDB(appContext)


    fun loadCoinList() : ArrayList<Coin>{
       return localData.readMoney()
    }

    fun syncCoinList(callBack: (ArrayList<Coin>) -> Unit){
        realtimeData.pullData(Coin::class.java) {
            localData.writeToLocalDB(it)
            callBack(it)
        }
    }

    fun loadCountryList(callBack: (ArrayList<Country>) -> Unit){
        realtimeData.pullData(Country::class.java){
            //Todo localData.insert(Arralists<Country>)
            callBack(it)
        }
    }

    fun terminateProvider(){
        localData.closeDb()
    }


}