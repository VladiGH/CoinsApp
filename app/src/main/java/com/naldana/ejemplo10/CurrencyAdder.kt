package com.naldana.ejemplo10

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import com.naldana.ejemplo10.models.Coin
import com.naldana.ejemplo10.database.RealTimeDatabase

class CurrencyAdder : AppCompatActivity() {

    private val database = RealTimeDatabase()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_adder)
        findViewById<Button>(R.id.submit).setOnClickListener {
            database.insertData(gater()) { success, msm ->
                Snackbar.make(
                    it,
                    "${if (success) {
                        getString(R.string.fb_ok)
                    } else {
                        getString(R.string.fb_error)
                    }}: $msm",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun gater(): Coin = Coin(
        null,
        findViewById<EditText>(R.id.editText_name).text.toString(),
        findViewById<EditText>(R.id.editText_country).text.toString(),
        findViewById<EditText>(R.id.editText_year).text.toString().toInt(),
        findViewById<CheckBox>(R.id.available).isChecked
    )
}
