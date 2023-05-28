package com.intermediate.substory.model

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResult(
    val userId: String,
    val name: String,
    val token: String
)

data class LoginResponse(
    val error: Boolean,
    val message: String,
    val loginResult: LoginResult?
)