package com.naldana.ejemplo10.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.widget.Button
import android.widget.EditText
import com.naldana.ejemplo10.R
import com.naldana.ejemplo10.database.RealTimeDatabase
import com.naldana.ejemplo10.models.Country

class CurrencyAdderCountry : AppCompatActivity() {

    private val database = RealTimeDatabase()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_adder2)
        findViewById<Button>(R.id.submit1).setOnClickListener{
            database.insertData(gatter()){ success, msm ->
                Snackbar.make(
                        it,
                        when {
                            success -> getString(R.string.fb_ok)
                            msm == "" -> getString(R.string.fb_error)
                            else -> msm
                        },
                        Snackbar.LENGTH_LONG
                    ).show()
            }
        }
    }
    private fun gatter(): Country = Country().apply {
        _id = null
        name = findViewById<EditText>(R.id.editText_name1).text.toString().toLowerCase()
        area = findViewById<EditText>(R.id.editText_country1).text.toString().toLowerCase()
    }

}


