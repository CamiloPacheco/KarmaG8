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

    val msgResponse = MutableLiveData<List<Msg>>()
    val messageList = mutableListOf<Msg>()
    private val database = Firebase.database.reference
    //val myRef = database.getReference("messages")
    
    init{
        viewMsg()
    }

    fun setMsg(msg: Msg) {
        database.child("messages").push().setValue(msg)
        viewMsg()
    }

    fun getMsg() = msgResponse
    fun viewMsg() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                messageList.clear()
                for (childDataSnapshot in dataSnapshot.children) {
                    val message: Msg = childDataSnapshot.getValue(Msg::class.java)!!
                    //Log.v("MyOut", "" + childDataSnapshot.getKey()); //displays the key for the node
                    //Log.v("MyOut", "" + message.id);
                    messageList.add(message)
                }
                msgResponse.value = messageList

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("MyOut", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }
        database.child("messages").addValueEventListener(postListener)
    }
}