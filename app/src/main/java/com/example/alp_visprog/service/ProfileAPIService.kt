package com.example.alp_visprog.service

import com.example.alp_visprog.model.ProfileModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProfileAPIService {
    @GET("profile")
    suspend fun getProfile(): ProfileModel

    @PUT("profile/{id}")
    suspend fun updateProfile(
        @Path("id") profileId: String,
        @Body profile: ProfileModel
    ): ProfileModel
}

object RetrofitClient {
    private const val BASE_URL = "http://192.168.157.206/"

    val profileService: ProfileAPIService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProfileAPIService::class.java)
    }
}