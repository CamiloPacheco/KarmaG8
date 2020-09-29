package com.cacomas.karmag8.viewmodel

import androidx.lifecycle.ViewModel
import com.cacomas.karmag8.repository.MsgRepository

class MsgViewModel : ViewModel() {
    private val repository = MsgRepository()
    fun SetMsg(msg: String)= repository.SetMsg(msg)
    fun getMsg()=repository.GetMsg()
    fun ViewMsg()=repository.ViewMsg()
}