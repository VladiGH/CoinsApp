package com.naldana.ejemplo10.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import com.naldana.ejemplo10.R
import com.naldana.ejemplo10.fragmentos.MoneyFragment
import com.naldana.ejemplo10.models.Coin

class DetailMoney : AppCompatActivity() {

    //private val moneyFragment = MoneyFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_money)


        val coinInfo = intent.extras?.getParcelable<Coin>("coin")

        val moneyFragment = MoneyFragment.newInstance(coinInfo)
        supportFragmentManager.beginTransaction().replace(R.id.content_fragment, moneyFragment).commit()

    }


}
