package com.cacomas.karmag8.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.cacomas.karmag8.model.FavorRealizado
import com.cacomas.karmag8.model.Karma
import com.cacomas.karmag8.model.Msg
import com.cacomas.karmag8.model.User
import com.cacomas.karmag8.util.PreferenceProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class ProfileRepository {

    val favorResponse = MutableLiveData<List<FavorRealizado>>()
    val favorList = mutableListOf<FavorRealizado>()
    val karmaResponse = MutableLiveData<List<Karma>>()
    val karmaList = mutableListOf<Karma>()
    val userResponse = MutableLiveData<List<User>>()
    val userList = mutableListOf<User>()
    private val database = Firebase.database.reference

    init{
        viewUser()
    }

    fun getFavores() = favorResponse
    fun getKarma() = karmaResponse
    fun getUser() = userResponse

    fun viewFavor() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                favorList.clear()
                for (childDataSnapshot in dataSnapshot.children) {
                    //val message: Msg = childDataSnapshot.getValue(Msg::class.java)!!
                    val favor: FavorRealizado = childDataSnapshot.getValue(FavorRealizado::class.java)!!
                    //Log.v("MyOut", "" + childDataSnapshot.getKey()); //displays the key for the node
                    Log.v("MyOut", "usuario del favor: " + favor.User);
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
        database.child("Favores realizados").addValueEventListener(postListener)
    }

    fun viewKarma() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                karmaList.clear()
                for (childDataSnapshot in dataSnapshot.children) {
                    //val message: Msg = childDataSnapshot.getValue(Msg::class.java)!!
                    val karma: Karma = childDataSnapshot.getValue(Karma::class.java)!!
                    //Log.v("MyOut", "" + childDataSnapshot.getKey()); //displays the key for the node
                    Log.v("MyOut", "Puntos:" + karma.puntos+" usuario: "+karma.user);
                    karmaList.add(karma)
                }
                karmaResponse.value = karmaList

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("MyOut", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }
        database.child("Karma").addValueEventListener(postListener)
    }

    fun viewUser(){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                userList.clear()
                for (childDataSnapshot in dataSnapshot.children) {
                    //val message: Msg = childDataSnapshot.getValue(Msg::class.java)!!
                    val usuarios: User = childDataSnapshot.getValue(User::class.java)!!
                    //Log.v("MyOut", "" + childDataSnapshot.getKey()); //displays the key for the node
                    Log.v("MyOut", "nombre de usuario " + usuarios.username);
                    userList.add(usuarios)
                }
                userResponse.value = userList

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("MyOut", "loadPost:onCancelled", databaseError.toException())
                // ...
            }

        }
        database.child("users").addValueEventListener(postListener)
        viewFavor()
        viewKarma()
    }

    fun logOut(){
        PreferenceProvider.setLogged("")
        Firebase.auth.signOut()
    }

}