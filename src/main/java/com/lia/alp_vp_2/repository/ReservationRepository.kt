package com.lia.alp_vp_2.repository

import com.lia.alp_vp_2.model.ReservationModel
import com.lia.alp_vp_2.service.ReservationService
import retrofit2.Response

interface ReservationRepository {
    suspend fun getUserReservations(userId: Int): Response<List<ReservationModel>>
}

class NetworkReservationRepository(
    private val reservationService: ReservationService
) : ReservationRepository {
    override suspend fun getUserReservations(userId: Int): Response<List<ReservationModel>> {
        return reservationService.getUserReservations(userId)
    }
}

