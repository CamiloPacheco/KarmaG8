package com.cacomas.karmag8.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.cacomas.karmag8.model.Msg
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class MsgRepository {
    val msgResponse = MutableLiveData<Msg>()
    val database = Firebase.database.reference
    //val myRef = database.getReference("messages")

    fun SetMsg( msg: String){
        database.child("messsages").push().setValue(msg)
    }
    fun GetMsg()=msgResponse
    fun ViewMsg(){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Msg object
                val msg = dataSnapshot.getValue<Msg>()
                msgResponse.postValue(msg)

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "load Msg:onCancelled", databaseError.toException())

            }
        }
        database.addValueEventListener(postListener)
    }
}