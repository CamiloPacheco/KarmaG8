package com.cacomas.karmag8.model
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Favor (
    val name: String? = "",
    val state: String? = ""
)