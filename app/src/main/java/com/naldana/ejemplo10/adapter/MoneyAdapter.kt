package com.naldana.ejemplo10.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.naldana.ejemplo10.R
import com.naldana.ejemplo10.models.Coin
import kotlinx.android.synthetic.main.cardview_coin.view.*

class MoneyAdapter(
    var coins: List<Coin>,
    val clickListener: (Coin) -> Unit
) : RecyclerView.Adapter<MoneyAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Coin, clickListener: (Coin) -> Unit) = with(itemView) {
            name_coin_cv.text = item.name
            country_coin_cv.text = item.country
            this.setOnClickListener { clickListener(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_coin, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return coins.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(coins[position], clickListener)
    }

    fun sortDataByName(upToDown: Boolean = true){
        coins = if(upToDown){
            coins.sortedBy{it.name.toLowerCase()}
        } else {
            coins.sortedByDescending{it.name.toLowerCase()}
        }
        notifyDataSetChanged()
    }

    fun filterByCountry(countryName: String){
        val new = coins.filter {
            it.country == countryName
        }
        coins = new
        notifyDataSetChanged()
    }

}