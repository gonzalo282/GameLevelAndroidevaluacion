package com.tunombre.gamelevelandroid.data.model

data class User(
    val id: Int = 0,
    val nombre: String = "",
    val email: String = "",
    val telefono: String = "",
    val direccion: String = "",
    val token: String? = null
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val nombre: String,
    val email: String,
    val password: String,
    val telefono: String,
    val direccion: String
)

data class AuthResponse(
    val success: Boolean,
    val message: String,
    val user: User? = null,
    val token: String? = null
)
