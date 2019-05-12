package com.naldana.ejemplo10.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.naldana.ejemplo10.R
import com.naldana.ejemplo10.fragmentos.MoneyFragment
import com.naldana.ejemplo10.models.Coin

class DetailMoney : AppCompatActivity() {

    private val moneyFragment = MoneyFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_money)
        val coinInfo = intent.extras?.getParcelable<Coin>("coin")
        supportFragmentManager.beginTransaction().replace(R.id.content_fragment2, moneyFragment).commit()
        moneyFragment.setData(coinInfo?:Coin())
    }


}
