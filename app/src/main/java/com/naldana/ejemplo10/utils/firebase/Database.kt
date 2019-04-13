package com.naldana.ejemplo10.utils.firebase

import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.naldana.ejemplo10.pojo.Coin

class Database {

    private val TAG = "Firebase"

    fun addCurrency(dataCoin: Coin, dataRef: ArrayList<Coin>) {
        val database = FirebaseDatabase.getInstance()
            .reference.child("monedas").apply {
            addValueEventListener(
                object : ValueEventListener {

                    @RequiresApi(Build.VERSION_CODES.N)
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        val value = dataSnapshot.getValue(Coin::class.java)
                        Log.d(TAG, "Value is: ${value?.name}")
                        dataRef.indexOf(value)
                        dataRef.replaceAll { if (it.name == value?.name ){ value} else {it}  }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException())
                    }
                })
        }
        database.child("monedas").push().setValue(dataCoin)
    }

    fun fillData(dataRef: ArrayList<Coin>){
        FirebaseDatabase.getInstance()
            .reference.child("monedas").apply {
            addValueEventListener(
                object : ValueEventListener {

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        val tempdata = dataSnapshot.children
                        for (coin in tempdata){
                            dataRef.add(coin.getValue(Coin()::class.java)!!)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException())
                    }
                })
        }

    }


}