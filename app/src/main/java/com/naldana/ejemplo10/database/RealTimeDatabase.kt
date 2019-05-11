package com.naldana.ejemplo10.database

import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.naldana.ejemplo10.models.Coin
import com.naldana.ejemplo10.models.Country

class RealTimeDatabase {

    private val TAG = "FireBase"
    val database = FirebaseDatabase.getInstance()

    fun <E : Any> insertData(dataObject: E, notify: (Boolean, String) -> Unit) {
        database.reference
            .child("${dataObject::class.java.simpleName}s")
            .push().setValue(dataObject)
            .addOnSuccessListener {
                notify(true, dataObject::class.java.simpleName)
            }.addOnFailureListener {
                notify(false, it.message?:"")
            }
    }

    fun addCountry(country: Country) {
        val database = FirebaseDatabase.getInstance()
            .reference.apply {
            addValueEventListener(
                object : ValueEventListener {

                    @RequiresApi(Build.VERSION_CODES.N)
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        val value = dataSnapshot.getValue(Country::class.java)
                        Log.d(TAG, "Value is: ${value?.name}")
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException())
                    }
                })
        }
        database.child("countries").push().setValue(country)
    }

    fun fillData(dataRef: ArrayList<Coin>, afterMethod: () -> Unit) {
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
                        for (coinSnap in tempdata) {
                            val coin = coinSnap.getValue(Coin::class.java)
                            coin?._id = coinSnap.key
                            dataRef.add(coin!!)
                            Log.i("flow", "cargado de fierbase ${coin._id}")

                        }
                        afterMethod()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException())
                    }
                })
        }

    }

    fun updateCoutny(country: Country) {
        database.reference.child("countries").child(country._id!!).push().setValue(country)
    }

    fun getCountries(dataRef: ArrayList<Country>, callBack: () -> Unit) {
        database.reference.child("countries").apply {
            addValueEventListener(
                object : ValueEventListener {

                    @RequiresApi(Build.VERSION_CODES.N)
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val tempData = dataSnapshot.children
                        for (country in tempData) {
                            Log.i("flow", country.getValue(Country::class.java)?._id)
                            dataRef.add(country.getValue(Country::class.java)!!)
                        }
                        callBack()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException())
                    }
                })
        }
    }


}