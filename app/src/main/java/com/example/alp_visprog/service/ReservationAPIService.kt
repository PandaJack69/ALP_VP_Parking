package com.example.alp_visprog.service

import com.example.alp_visprog.model.ReservationModel
import com.example.alp_visprog.model.ReservationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ReservationAPIService {
    @GET("api/reservations/user/{userId}")
    suspend fun getUserReservations(@Path("userId") userId: Int): Response<List<ReservationModel>>

    @PUT("api/reservations/{reservationId}/status")
    suspend fun updateReservationStatus(
        @Path("reservationId") reservationId: Int,
        @Path("status") status: String
    ): Response<Unit>

    @GET("/reservations/{userId}")
    suspend fun getReservationByUser(@Path("userId") userId: String): ReservationResponse
}
