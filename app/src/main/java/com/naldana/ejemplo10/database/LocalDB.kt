package com.naldana.ejemplo10.database

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.naldana.ejemplo10.models.Coin

class LocalDB(appContext: Context) {

    private val dbHelper = DatabaseSQL(appContext)

    fun closeDb() {
        dbHelper.close()
    }

    fun writeToLocalDB(data: ArrayList<Coin>): Boolean {
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
                Log.i("MainActivity", "From local firebaseRTDB ${coin.name} ${coin.year}")
                lista.add(coin)
            }
        }

        return lista
    }

}