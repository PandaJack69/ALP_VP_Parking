package com.example.alp_visprog.repository

import com.example.alp_visprog.model.PenaltyModel
import com.example.alp_visprog.model.PenaltyModelItem
import com.example.alp_visprog.service.PenaltyAPIService

interface PenaltyRepository {
    suspend fun getAllPenalties(token: String): PenaltyModel
    suspend fun getPenalty(token: String, penaltyId: Int): PenaltyModelItem
}

class NetworkPenaltyRepository(
    private val penaltyAPIService: PenaltyAPIService
) : PenaltyRepository {
    override suspend fun getAllPenalties(token: String): PenaltyModel {
        return penaltyAPIService.getAllPenalty(token)
    }

    override suspend fun getPenalty(token: String, penaltyId: Int): PenaltyModelItem {
        // Make sure the function in your service is a suspend function as well
        return penaltyAPIService.getPenalty(token, penaltyId)
    }
}
