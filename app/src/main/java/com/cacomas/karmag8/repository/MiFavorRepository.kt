package com.cacomas.karmag8.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.cacomas.karmag8.model.Favor
import com.cacomas.karmag8.model.Karma
import com.cacomas.karmag8.model.Msg
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MiFavorRepository {
    val favorResponse = MutableLiveData<List<Favor>>()
    val favorList = mutableListOf<Favor>()
    val karmaResponse = MutableLiveData<List<Karma>>()
    val karmaList = mutableListOf<Karma>()

    private val database = Firebase.database.reference
    init{
        viewKarma()
    }
    fun setFavor(favor:Favor) {
        database.child("Mifavor").push().setValue(favor)
        viewFavor()
    }
    fun getFavor() = favorResponse

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
                    Log.v("MyOut", "Lista sin ordenar "+karmaList)
                }
                karmaList.sortByDescending { it.puntos }
                Log.v("MyOut", "Lista ordenada "+karmaList);
                karmaResponse.value = karmaList

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("MyOut", "loadPost:onCancelled", databaseError.toException())
                // ...
            }

        }
        database.child("Karma").addValueEventListener(postListener)
        viewFavor()
    }
    fun viewFavor() {

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                favorList.clear()
                for (childDataSnapshot in dataSnapshot.children) {
                    val favor: Favor = childDataSnapshot.getValue(Favor::class.java)!!
                    favorList.add(favor)
                }

                val favoresFiltrados = ArrayList<Favor>()
                for (karma in karmaList !!) {
                    Log.v("MyOut", "Entra al para karma ")
                    for (favor in favorList) {
                        Log.v("MyOut", "Entra al favor: " + favor.name + " con usuario: " + favor.user)
                        if (favor.user == karma.user ) {
                            favoresFiltrados.add(favor)
                        }
                    }
                }
                favorResponse.value = favoresFiltrados

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("MyOut", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }
        database.child("Mifavor").addValueEventListener(postListener)
    }
    fun updateFavor(favorAux:Favor) {
        var key:String=""
        database.child("Mifavor")
            .orderByChild("user")
            .equalTo(favorAux.user)
            .addListenerForSingleValueEvent(object: ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    dataSnapshot.children.forEach {
                        //"it" is the snapshot
                        val key: String = it.key.toString()
                        database.child("Mifavor").child(key).child("state").setValue("Asignado")
                        database.child("Mifavor").child(key).child("realizadopor").setValue(favorAux.realizadopor)
                    }
                }

                override fun onCancelled(p0: DatabaseError) {
                    //do whatever you need
                }
            })

    }
    fun endFavor(favorAux:Favor,currentUser:String) {
        var key:String=""
        database.child("Mifavor")
            .orderByChild("user")
            .equalTo(favorAux.user)
            .addListenerForSingleValueEvent(object: ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    dataSnapshot.children.forEach {
                        //"it" is the snapshot

                        val favor: Favor = it.getValue(Favor::class.java)!!
                        if(favor.user==currentUser) {
                            database.child("Mifavor").child(it.key.toString())
                                .child("terminado1").setValue("1")
                        }
                        else {
                            if (favor.realizadopor == currentUser) {
                                database.child("Mifavor").child(it.key.toString())
                                    .child("terminado2").setValue("1")
                            }
                        }
                        if (favor.terminado1=="1" || favor.terminado2=="1"){
                            database.child("Favores realizados").push().setValue(favor)
                            database.child("Mifavor").child(it.key.toString()).removeValue()
                            favor.realizadopor?.let { KarmaPoint(it,"1") }
                        }
                    }
                }

                override fun onCancelled(p0: DatabaseError) {
                    //do whatever you need
                }
            })

    }
    fun KarmaPoint(user:String, sw:String) {

        database.child("Karma")
            .orderByChild("user")
            .equalTo(user)
            .addListenerForSingleValueEvent(object: ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    dataSnapshot.children.forEach {
                        //"it" is the snapshot
                        val karma: Karma= it.getValue(Karma::class.java)!!
                        if(user==karma.user){
                            var puntos: Int? =karma.puntos?.toInt()
                            if (sw=="1") {
                                if (puntos != null) {
                                    puntos=puntos+1
                                }
                                database.child("Karma").child(it.key.toString())
                                    .child("puntos").setValue(puntos.toString())
                            }
                            else {
                                if (puntos != null) {
                                    puntos=puntos-2
                                }
                                database.child("Karma").child(it.key.toString())
                                    .child("puntos").setValue(puntos.toString())
                            }
                        }
                    }
                }

                override fun onCancelled(p0: DatabaseError) {
                    //do whatever you need
                }
            })

    }

}