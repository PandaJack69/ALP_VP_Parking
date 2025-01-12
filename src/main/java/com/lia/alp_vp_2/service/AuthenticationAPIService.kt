package com.lia.alp_vp_2.service

import com.lia.alp_vp_2.model.UserResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationAPIService {
    @POST("api/register")
    suspend fun register(@Body registerMap: Map<String, String>): UserResponse

    @POST("api/login")
    suspend fun login(@Body loginMap: Map<String, String>): UserResponse
}

