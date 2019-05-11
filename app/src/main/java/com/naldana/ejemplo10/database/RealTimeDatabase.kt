package com.naldana.ejemplo10.database

import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.reflect.Method

class RealTimeDatabase {

    private val tag = this@RealTimeDatabase::class.java.simpleName
    private val fireBase = FirebaseDatabase.getInstance()

    fun <E : Any> insertData(dataObject: E, notify: (Boolean, String) -> Unit) {
        fireBase.reference
            .child("${dataObject::class.java.simpleName}s".toLowerCase())
            .push().setValue(dataObject)
            .addOnSuccessListener {
                notify(true, dataObject::class.java.simpleName)
            }.addOnFailureListener {
                notify(false, it.message ?: "")
            }
    }

    fun <E : Any> pullData(pullType: Class<E>, callBack: (ArrayList<E>) -> Unit) {
        Log.i(tag, "The name of node is ${pullType.simpleName}")
        val dataArray = ArrayList<E>()
        fireBase.getReference("${pullType.simpleName}s".toLowerCase()).apply {
            addListenerForSingleValueEvent(
                object : ValueEventListener {
                    @RequiresApi(Build.VERSION_CODES.N)

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        Log.d(tag, "Data snap change detected")
                        for (snap in dataSnapshot.children) {
                            val element = snap.getValue(pullType)
                            var setIdMethod: Method?
                            try {
                                setIdMethod = element!!::class.java.getMethod("set_id", kotlin.String::class.java)
                                if (setIdMethod != null) {
                                    setIdMethod.invoke(element, snap.key ?: "")
                                    dataArray.add(element)
                                }
                            } catch (e: NoSuchMethodException) {
                                Log.i(tag, "The object ${pullType.simpleName} does not have set_id(String) method")
                            }
                        }
                        callBack(dataArray)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.w(tag, "Failed to read value.", error.toException())
                        callBack(dataArray)
                    }
                })
        }
    }


}