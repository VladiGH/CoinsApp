package com.naldana.ejemplo10.fragmentos

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.naldana.ejemplo10.R
import com.naldana.ejemplo10.models.Coin

class MoneyFragment : Fragment() {

    private lateinit var fragmentViewRef: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        fragmentViewRef = inflater.inflate(R.layout.money_detail, container, false)
        fragmentViewRef.findViewById<TextView>(R.id.year_text).text = "Year: currentCoin.year.toString()"
        return fragmentViewRef
    }

    fun setData(currentCoin: Coin){
        fragmentViewRef.findViewById<TextView>(R.id.year_text).text = "Year: "+currentCoin.year.toString()
        fragmentViewRef.findViewById<TextView>(R.id.name_text).text = "Nombre: "+currentCoin.name
        fragmentViewRef.findViewById<TextView>(R.id.country_text).text = "Country: "+currentCoin.country
        fragmentViewRef.findViewById<TextView>(R.id.available_text).text = "Is available?: " +currentCoin.available.toString()
    }

}