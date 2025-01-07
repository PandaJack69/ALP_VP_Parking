package com.example.alp_visprog.model

data class UserResponse(
    val data: UserModel
)

data class UserModel (
    val username: String,
    val token: String?
)