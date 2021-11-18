package com.uas.agenda.api.model

data class LoginResponse(
    val head: Datos
)

data class Datos(
    val status: String,
    val code: Int,
    val message: String
)
