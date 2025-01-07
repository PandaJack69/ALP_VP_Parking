package com.example.alp_visprog.service

import android.service.autofill.UserData
import com.example.alp_visprog.model.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthenticationAPIService {
    @POST("api/register")
    fun register(@Body registerMap: HashMap<String, String> ): Call<UserResponse>

    @POST("api/login")
    fun login(@Body loginMap: HashMap<String, String>): Call<UserResponse>
}