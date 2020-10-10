package com.cacomas.karmag8.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.cacomas.karmag8.model.Msg
import com.cacomas.karmag8.model.User
import dagger.Provides
import javax.inject.Singleton
import com.cacomas.karmag8.util.PreferenceProvider

class LoginRepository {

    private lateinit var auth: FirebaseAuth
    val database = Firebase.database.reference
    var logged = MutableLiveData<String>()
    var userCreated = MutableLiveData<Boolean>()

    init {
        auth = Firebase.auth
        logged.value = ""
    }

    fun writeNewUser(user: User){
        database.child("users").push().setValue(user)
    }

    fun signIn(email: String, password : String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        Log.d("MyOut", "signInWithEmailAndPassword:success " + user.email)
                        Log.d("MyOut", "signInWithEmailAndPassword:success " + user.uid)
                        logged.value = user.uid
                        PreferenceProvider.setLogged(user.uid)
                    }

                } else {
                    logged.value = ""
                    Log.d("MyOut", "signInWithEmailAndPassword:failure", task.exception)
                }
            }
    }


}