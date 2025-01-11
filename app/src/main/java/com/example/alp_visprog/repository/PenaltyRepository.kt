package com.example.alp_visprog.repository

import com.example.alp_visprog.model.PenaltyModel
import com.example.alp_visprog.model.PenaltyModelItem
import com.example.alp_visprog.service.PenaltyAPIService
import retrofit2.Call

interface PenaltyRepository {
    fun getAllPenalties(token: String): Call<PenaltyModel>
    fun getPenalty(token: String, penaltyId: Int): Call<PenaltyModelItem>
}

class NetworkPenaltyRepository(
    private val penaltyAPIService: PenaltyAPIService
) : PenaltyRepository {
    override fun getAllPenalties(token: String): Call<PenaltyModel> {
        return penaltyAPIService.getAllPenalty(token)
    }

    override fun getPenalty(token: String, penaltyId: Int): Call<PenaltyModelItem> {
        return penaltyAPIService.getPenalty(token, penaltyId)
    }
}