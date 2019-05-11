package com.naldana.ejemplo10.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

private const val SQL_CREATE_COINS =
    "CREATE TABLE ${DatabaseContract.CoinEntry.TABLE_NAME} (" +
            "${DatabaseContract.CoinEntry.COLUMN_ID} TEXT PRIMARY KEY," +
            "${DatabaseContract.CoinEntry.COLUMN_NAME} TEXT," +
            "${DatabaseContract.CoinEntry.COLUMN_COUNTRY} TEXT," +
            "${DatabaseContract.CoinEntry.COLUMN_YEAR} TEXT," +
            "${DatabaseContract.CoinEntry.COLUMN_AVAILABLE} INTEGER); " +
            "CREATE TABLE ${DatabaseContract.CountryEntry.TABLE_NAME} (" +
            "${DatabaseContract.CountryEntry.COLUMN_ID} TEXT PRIMARY KEY," +
            "${DatabaseContract.CountryEntry.COLUMN_NAME} TEXT)"


private const val SQL_DELETE_COINS = "DROP TABLE IF EXISTS ${DatabaseContract.CoinEntry.TABLE_NAME}"
private const val SQL_DELETE_COUNTRIES = "DROP TABLE IF EXISTS ${DatabaseContract.CountryEntry.TABLE_NAME}"

class DatabaseSQL(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // TODO(8) Este método se ejecuta cuando es necesario crear el esquema de base de datos.
    // TODO(8.1) Este recibe como parámetro una instancia de SQLiteDatabase y que permite ejecutar SQL de DDL.
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_COINS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // TODO (10) Cuando la base de datos ya existe y se define una nueva versión, se deben ejecutar scripts de migración.
        db.execSQL(SQL_DELETE_COINS)
        db.execSQL(SQL_DELETE_COUNTRIES)
        onCreate(db)
    }


    // TODO(11) Se definen en constantes, el nombre de la base de datos y la versión
    companion object {
        const val DATABASE_NAME = "miprimerabase.db"
        const val DATABASE_VERSION = 4
    }
}