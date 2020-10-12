package com.cacomas.karmag8.model
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Msg (
        val txt: String? = "",
        val from: String? = "",
        val to: String? = ""
)