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
    private val dataProvider = RealTimeDatabase()
    private val dbHelper = DatabaseSQL(appContext)
    private val coinList = ArrayList<Coin>()
    private val countryList = ArrayList<Country>()
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

    private fun readMonedas(): ArrayList<Coin> {
        val db = dbHelper.readableDatabase
        val projection = arrayOf(
            DatabaseContract.CoinEntry.COLUMN_NAME,
            DatabaseContract.CoinEntry.COLUMN_COUNTRY,
            DatabaseContract.CoinEntry.COLUMN_YEAR,
            DatabaseContract.CoinEntry.COLUMN_AVAILABLE
        )

        val sortOrder = "${DatabaseContract.CoinEntry.COLUMN_NAME} DESC"

        val cursor = db.query(
            DatabaseContract.CoinEntry.TABLE_NAME, // nombre de la tabla
            projection, // columnas que se devolverán
            null, // Columns where clausule
            null, // values Where clausule
            null, // Do not group rows
            null, // do not filter by row
            sortOrder // sort order
        )

        val lista = ArrayList<Coin>()

        with(cursor) {
            while (moveToNext()) {
                val coin = Coin(
                    null,
                    getString(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_NAME)),
                    getString(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_COUNTRY)),
                    getInt(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_YEAR)),
                    getInt(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_AVAILABLE)) == 1
                )
                Log.i("MainActivity", "From local database ${coin.name} ${coin.year}")
                lista.add(coin)
            }
        }

        return lista
    }

    private fun writeToLocalDB(data: ArrayList<Coin>): Boolean {
        val db = dbHelper.writableDatabase
        val listBase = readMonedas()
        data.forEach {
            val values = ContentValues().apply {
                put(DatabaseContract.CoinEntry.COLUMN_NAME, it.name)
                put(DatabaseContract.CoinEntry.COLUMN_COUNTRY, it.country)
                put(DatabaseContract.CoinEntry.COLUMN_YEAR, it.year)
                put(DatabaseContract.CoinEntry.COLUMN_AVAILABLE, if (it.available) 1 else 0)
            }
            if (!listBase.contains(it)) {
                val newRowId = db?.insert(DatabaseContract.CoinEntry.TABLE_NAME, null, values)
                return newRowId == -1L
            }
        }
        return true
    }
}