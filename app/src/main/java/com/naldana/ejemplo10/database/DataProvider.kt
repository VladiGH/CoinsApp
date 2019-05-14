package com.naldana.ejemplo10.database

import android.content.Context
import android.util.Log
import com.naldana.ejemplo10.models.Coin
import com.naldana.ejemplo10.models.Country


class DataProvider(appContext: Context) {

    private val tag = this@DataProvider::class.java.simpleName
    private val realtimeData = RealTimeDatabase()
    private val localData = LocalDB(appContext)


    fun loadCoinList() : ArrayList<Coin>{
       return localData.readMoney()
    }

    fun syncCoinList(callBack: (ArrayList<Boolean>) -> Unit){
        realtimeData.pullData(Coin::class.java) {
            callBack(localData.writeToLocalDB(it))
        }
    }

    fun loadCountryList(callBack: (ArrayList<Country>) -> Unit){
        realtimeData.pullData(Country::class.java){
            //Todo vlady localData.insert(Arralists<Country>)

            callBack(it)
        }
    }

    fun syncCountryList(callBack: (ArrayList<Boolean>) -> Unit){
        realtimeData.pullData(Country::class.java) {
            //Todo Raul crear metodo para guaradar paises en local DB
            callBack(localData.insertToLocalDB(it))
        }
    }

    fun terminateProvider(){
        localData.closeDb()
    }


}