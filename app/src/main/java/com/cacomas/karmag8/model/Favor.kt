package com.cacomas.karmag8.model
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Favor (
    val name: String? = "",
    val state: String? = "",
    val user: String? = "",
    val descripcion: String? = "",
    val terminado1: String? = "",
    val terminado2: String? = "",
    var realizadopor: String? = "",
    var adress: String? = ""
)