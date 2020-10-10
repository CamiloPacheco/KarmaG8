package com.cacomas.karmag8.model
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class FavorRealizado(
    val Estado: String? = "",
    val RealizadoPor: String? = "",
    val Tipo: String? = "",
    val User: String? = ""
)