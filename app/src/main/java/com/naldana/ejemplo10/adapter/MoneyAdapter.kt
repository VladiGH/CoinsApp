package com.naldana.ejemplo10.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.naldana.ejemplo10.R
import com.naldana.ejemplo10.pojo.Coin
import kotlinx.android.synthetic.main.cardview_coin.view.*

class MoneyAdapter(var coins: List<Coin>, val clickListener: (Coin)-> Unit): RecyclerView.Adapter<MoneyAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoneyAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_coin, parent, false)
        return ViewHolder(view)
    }

//TODO: HACER LA VIEW DE CADA MONEDA
    override fun getItemCount(): Int {
        return coins.size
    }

    override fun onBindViewHolder(holder: MoneyAdapter.ViewHolder, position: Int) {
        return holder.bind(coins[position], clickListener)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(item: Coin, clickListener: (Coin) -> Unit) = with(itemView) {
            name_coin_cv.text = item.name
            country_coin_cv.text = item.country
            this.setOnClickListener{clickListener(item)}
        }
    }


}