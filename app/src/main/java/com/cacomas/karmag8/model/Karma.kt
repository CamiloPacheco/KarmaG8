package com.cacomas.karmag8.model
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Karma (
    val user: String? = "",
    val puntos: String? = ""
)