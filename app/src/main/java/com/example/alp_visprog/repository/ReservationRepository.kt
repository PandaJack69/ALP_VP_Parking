package com.example.alp_visprog.repository

import com.example.alp_visprog.model.ReservationModel
import com.example.alp_visprog.model.ReservationResponse
import com.example.alp_visprog.service.ReservationAPIService
import retrofit2.Response

interface ReservationRepository {
    suspend fun getUserReservations(userId: Int): Response<List<ReservationModel>>
    suspend fun updateStatus(reservationId: Int, status: String): Response<Unit>
    suspend fun getReservationByUser(userId: String): ReservationResponse // Added to interface
}

class NetworkReservationRepository(
    private val reservationService: ReservationAPIService
) : ReservationRepository {
    override suspend fun getUserReservations(userId: Int): Response<List<ReservationModel>> {
        return reservationService.getUserReservations(userId)
    }

    override suspend fun updateStatus(reservationId: Int, status: String): Response<Unit> {
        return reservationService.updateReservationStatus(reservationId, status)
    }

    override suspend fun getReservationByUser(userId: String): ReservationResponse {
        return reservationService.getReservationByUser(userId) // Use the instance
    }


}
