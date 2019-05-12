package com.naldana.ejemplo10.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

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

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_COINS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_COINS)
        db.execSQL(SQL_DELETE_COUNTRIES)
        onCreate(db)
    }

    companion object {
        const val DATABASE_NAME = "miprimerabase.db"
        const val DATABASE_VERSION = 4
    }
}