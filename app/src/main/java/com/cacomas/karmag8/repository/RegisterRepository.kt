package com.cacomas.karmag8.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.cacomas.karmag8.model.Karma
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.cacomas.karmag8.model.Msg
import com.cacomas.karmag8.model.User
import com.google.firebase.auth.UserProfileChangeRequest
import dagger.Provides
import javax.inject.Singleton

class RegisterRepository {
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

    fun signUp(email: String, password : String, username : String){
        Log.d("MyOut","email="+email+" contraseña= "+password)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("MyOut", "createUserWithEmail:success")
                    val user = auth.currentUser
                    if (user != null) {
                        writeNewUser(User(user.email, user.uid,username))
                        var karma = Karma(username,"2")
                        database.child("Karma").push().setValue(karma)
                    }
                    userCreated.value = true;
                } else {
                    Log.d("MyOut", "createUserWithEmail:failure", task.exception)
                    userCreated.value = false;
                }
            }
    }


}