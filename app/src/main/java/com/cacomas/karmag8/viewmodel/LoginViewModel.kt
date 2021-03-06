package com.cacomas.karmag8.viewmodel

import android.content.Context
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.cacomas.karmag8.model.Msg
import com.cacomas.karmag8.model.User
import com.cacomas.karmag8.repository.LoginRepository
import com.cacomas.karmag8.repository.RegisterRepository
import javax.inject.Inject

class LoginViewModel : ViewModel() {
    private val repository = LoginRepository()

    fun logged() = repository.logged

    fun userCreated() = repository.userCreated

    fun signIn(email: String, password : String){
        repository.signIn(email,password)
    }



}