package com.cacomas.karmag8.viewmodel

import androidx.lifecycle.ViewModel
import com.cacomas.karmag8.model.Msg
import com.cacomas.karmag8.repository.MsgRepository

class MsgViewModel : ViewModel() {

    private val repository = MsgRepository()
    fun setMsg(msg: Msg)= repository.setMsg(msg)
    fun getMsg()=repository.getMsg()
}