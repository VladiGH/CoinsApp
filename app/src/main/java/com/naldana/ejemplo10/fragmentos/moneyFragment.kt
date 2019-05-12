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

    lateinit var sho: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.money_detail, container, false)
        sho = view
        return view
    }

    fun setData(currentCoin: Coin){
        sho.findViewById<TextView>(R.id.year_text).text = "Year: "+currentCoin.year.toString()
        sho.findViewById<TextView>(R.id.name_text).text = "Nombre: "+currentCoin.name
        sho.findViewById<TextView>(R.id.country_text).text = "Country: "+currentCoin.country
        sho.findViewById<TextView>(R.id.available_text).text = "Is available?: " +currentCoin.available.toString()
    }

}