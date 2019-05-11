package com.naldana.ejemplo10.database

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.naldana.ejemplo10.models.Coin
import com.naldana.ejemplo10.models.Country
import java.lang.reflect.Method

class DataProvider(appContext: Context) {

    private val tag = this@DataProvider::class.java.simpleName
    private val dbHelper = DatabaseSQL(appContext)
    private val coinList = ArrayList<Coin>()
    private val countryList = ArrayList<Country>()
    private val localDB = LocalDB(appContext)
    private val firebaseRTDB = FirebaseDatabase.getInstance()


    fun closeDb() {
        dbHelper.close()
    }

    fun <E : Any> pullData(pullType: Class<E>, callBack: (ArrayList<E>) -> Unit) {
        Log.i(tag, "The name of node is ${pullType.simpleName}")
        firebaseRTDB.getReference("monedas").apply {
            addListenerForSingleValueEvent(
                object : ValueEventListener {
                    @RequiresApi(Build.VERSION_CODES.N)

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        Log.d(tag, "Data snap change detected")
                        val dataArray = ArrayList<E>()
                        for (snap in dataSnapshot.children) {
                            val element = snap.getValue(pullType) ?: pullType.newInstance()
                            var setIdMethod: Method?
                            try {
                                setIdMethod = element::class.java.getMethod("set_id", kotlin.String::class.java)
                                if (setIdMethod != null) {
                                    setIdMethod.invoke(element, snap.key ?: "")
                                    dataArray.add(element)
                                }
                            } catch (e: NoSuchMethodException) {
                                Log.i(tag, "El objecto ${pullType.simpleName} no posee metodo set_id(String)")
                            }
                        }
                        callBack(dataArray)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.w(tag, "Failed to read value.", error.toException())
                    }
                })
        }
    }
}