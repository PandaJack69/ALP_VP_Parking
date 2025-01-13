package com.example.alp_visprog.model

data class UserResponse(
    val data: UserModel
)

data class UserModel (
    val username: String,
//    val token: String?,
    val firstName: String,
    val lastName: String,
    val nim: String,
    val licensePlate: String,
    val email: String,
    val token: String?,
    val profilePictureUrl: String? = null
)