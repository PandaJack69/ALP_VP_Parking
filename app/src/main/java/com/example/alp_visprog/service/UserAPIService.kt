package com.example.alp_visprog.service

import com.example.alp_visprog.model.GeneralResponseModel
import com.example.alp_visprog.model.ReservationModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface UserAPIService {
    @POST("api/logout")
    fun logout(@Header("X-API-TOKEN") token: String): Call<GeneralResponseModel>

    @GET("reservations/active")
    fun getActiveReservation(@Query("userId") userId: String): Call<ReservationModel>
}