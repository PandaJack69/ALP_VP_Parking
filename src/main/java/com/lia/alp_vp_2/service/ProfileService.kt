package com.lia.alp_vp_2.service

import com.lia.alp_vp_2.model.ProfileModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProfileService {
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

    val profileService: ProfileService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProfileService::class.java)
    }
}

