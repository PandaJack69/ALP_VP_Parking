package com.lia.alp_vp_2.service

import com.lia.alp_vp_2.model.GeneralResponseModel
import com.lia.alp_vp_2.model.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface UserAPIService {
    @GET("api/user")
    suspend fun getUserProfile(): UserResponse

    @PUT("api/user")
    suspend fun updateUserProfile(@Body updatedUser: Map<String, String>): UserResponse

    @POST("api/logout")
    fun logout(@Header("X-API-TOKEN") token: String): Call<GeneralResponseModel>
}

