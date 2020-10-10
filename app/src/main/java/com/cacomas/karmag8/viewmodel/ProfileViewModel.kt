package com.cacomas.karmag8.viewmodel

import androidx.lifecycle.ViewModel
import com.cacomas.karmag8.model.Msg
import com.cacomas.karmag8.repository.MsgRepository
import com.cacomas.karmag8.repository.ProfileRepository

class ProfileViewModel: ViewModel() {
    private val repository = ProfileRepository()
    fun getFavores()=repository.getFavores()
    fun getKarma()=repository.getKarma()
    fun getUser()=repository.getUser()
    fun logOut(){
        repository.logOut()
    }
}