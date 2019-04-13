package com.naldana.ejemplo10.firebase

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

    fun addCurrency(dataCoin: Coin) {
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
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException())
                    }
                })
        }
        database.child("monedas").push().setValue(dataCoin)
    }

    fun fillData(dataRef: ArrayList<Coin>, afterMetod: () -> Unit ){
        FirebaseDatabase.getInstance()
            .reference.child("monedas").apply {
            addValueEventListener(
                object : ValueEventListener {

                    @RequiresApi(Build.VERSION_CODES.N)
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        dataRef.clear()
                        val tempdata = dataSnapshot.children
                        for (coin in tempdata){
                            Log.i("flow",coin.getValue(Coin::class.java)?.name )
                            dataRef.add(coin.getValue(Coin::class.java)!!)
                        }
                        afterMetod()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException())
                    }
                })
        }

    }


}