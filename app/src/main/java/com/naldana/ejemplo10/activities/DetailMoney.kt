package com.naldana.ejemplo10.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import com.naldana.ejemplo10.R
import com.naldana.ejemplo10.fragmentos.MoneyFragment
import com.naldana.ejemplo10.models.Coin

class DetailMoney : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_money)

        var coinInfo: Coin = intent.extras.getParcelable("coin")

        Log.d("prueba",coinInfo.country)
        //initActivity(coinInfo)
    }

    fun initActivity(coin: Coin) {
        val moneyFragment = MoneyFragment()
        moneyFragment.setData(coin)

        changeFragment(R.id.content_fragment,moneyFragment)
    }

    private fun changeFragment(id: Int, frag: Fragment) {
        supportFragmentManager.beginTransaction().replace(id, frag).commit()
    }
}
