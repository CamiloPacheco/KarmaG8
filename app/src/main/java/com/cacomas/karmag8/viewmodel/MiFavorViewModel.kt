package com.cacomas.karmag8.viewmodel

import androidx.lifecycle.ViewModel
import com.cacomas.karmag8.model.Favor
import com.cacomas.karmag8.repository.MiFavorRepository

class MiFavorViewModel: ViewModel() {

    private val repository = MiFavorRepository()
    fun setFavor(favor: Favor)= repository.setFavor(favor)
    fun getFavor()=repository.getFavor()
    fun updateFavor(favor:Favor)=repository.updateFavor(favor)
    fun endFavor(favor:Favor,currentUser:String)=repository.endFavor(favor,currentUser)
    fun karmaPoint(user:String,sw: String)=repository.KarmaPoint(user,sw)
}