package com.naldana.ejemplo10

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import com.naldana.ejemplo10.pojo.Coin
import com.naldana.ejemplo10.utils.firebase.Database

class CurrencyAdder : AppCompatActivity() {

    companion object {
        val database = Database()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_adder)
        findViewById<Button>(R.id.submit).setOnClickListener{
            database.addCurrency(gater())
        }
    }

    private fun gater():Coin = Coin(
        findViewById<EditText>(R.id.editText_name).text.toString(),
        findViewById<EditText>(R.id.editText_country).text.toString(),
        findViewById<EditText>(R.id.editText_year).text.toString(),
        findViewById<CheckBox>(R.id.available).isChecked
    )
}
