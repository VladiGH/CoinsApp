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
    var coin : Coin? = Coin()

    private lateinit var fragmentViewRef: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        fragmentViewRef = inflater.inflate(R.layout.money_detail, container, false)
        //fragmentViewRef.findViewById<TextView>(R.id.year_text).text = "Year: currentCoin.year.toString()"
        setData(coin)
        return fragmentViewRef
    }

    fun setData(currentCoin: Coin?){
        fragmentViewRef.findViewById<TextView>(R.id.year_text).text = "Year: "+coin?.year.toString()
        fragmentViewRef.findViewById<TextView>(R.id.name_text).text = "Nombre: "+coin?.name
        fragmentViewRef.findViewById<TextView>(R.id.country_text).text = "Country: "+coin?.country
        fragmentViewRef.findViewById<TextView>(R.id.available_text).text = "Is available?: " +coin?.available.toString()
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(coin: Coin?):MoneyFragment{
            val newFragment= MoneyFragment()
            newFragment.coin = coin
            return newFragment

        }
    }

}