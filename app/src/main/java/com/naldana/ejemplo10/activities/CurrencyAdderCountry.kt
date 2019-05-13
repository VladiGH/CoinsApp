package com.naldana.ejemplo10.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.naldana.ejemplo10.R
import com.naldana.ejemplo10.database.RealTimeDatabase

class CurrencyAdderCountry : AppCompatActivity() {

    private val database = RealTimeDatabase()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_adder2)
    }

}
