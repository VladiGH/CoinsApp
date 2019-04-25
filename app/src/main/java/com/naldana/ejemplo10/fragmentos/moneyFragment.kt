package com.naldana.ejemplo10.fragmentos

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.naldana.ejemplo10.R

class MoneyFragment : Fragment() {

    lateinit var sho: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        sho = inflater.inflate(R.layout.money_detail, container, false)
        return sho
    }

    fun setData(currentCoin: Coin){
        sho.findViewById<TextView>(R.id.year_text).text = currentCoin.year.toString()
        sho.findViewById<TextView>(R.id.name_text).text = currentCoin.name
        sho.findViewById<TextView>(R.id.country_text).text = currentCoin.country
        sho.findViewById<TextView>(R.id.available_text).text = currentCoin.available.toString()
    }

}