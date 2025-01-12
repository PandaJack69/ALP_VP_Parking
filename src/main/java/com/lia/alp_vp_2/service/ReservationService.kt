package com.lia.alp_vp_2.service

import com.lia.alp_vp_2.model.ReservationModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ReservationService {
    @GET("api/reservations/user/{userId}")
    suspend fun getUserReservations(@Path("userId") userId: Int): Response<List<ReservationModel>>
}

