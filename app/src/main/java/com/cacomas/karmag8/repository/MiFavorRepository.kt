package com.cacomas.karmag8.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.cacomas.karmag8.model.Favor
import com.cacomas.karmag8.model.Msg
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MiFavorRepository {
    val favorResponse = MutableLiveData<List<Favor>>()
    val favorList = mutableListOf<Favor>()
    private val database = Firebase.database.reference


    init{
        viewFavor()
    }
    fun setFavor(favor:Favor) {
        database.child("Mifavor").push().setValue(favor)
        viewFavor()
    }
    fun getFavor() = favorResponse
    fun viewFavor() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                favorList.clear()
                for (childDataSnapshot in dataSnapshot.children) {
                    val favor: Favor = childDataSnapshot.getValue(Favor::class.java)!!
                    favorList.add(favor)
                }
                favorResponse.value = favorList

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("MyOut", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }
        database.child("Mifavor").addValueEventListener(postListener)
    }


}