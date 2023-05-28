package com.intermediate.substory.model

data class UserModel(
    val token : String,
    val name: String,
    val email: String,
    val password: String,
    val isLogin: Boolean
)