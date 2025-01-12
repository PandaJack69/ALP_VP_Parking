package com.lia.alp_vp_2.model

data class UserResponse(
    val data: UserModel
)

data class UserModel(
    val firstName: String,
    val lastName: String,
    val nim: String,
    val licensePlate: String,
    val email: String,
    val token: String?,
    val profilePictureUrl: String? = null
)

