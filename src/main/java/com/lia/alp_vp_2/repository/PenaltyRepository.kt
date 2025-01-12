package com.lia.alp_vp_2.repository

import com.lia.alp_vp_2.model.PenaltyModel
import com.lia.alp_vp_2.model.PenaltyModelItem
import com.lia.alp_vp_2.service.PenaltyAPIService
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

