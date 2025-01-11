package com.example.alp_visprog.service

import com.example.alp_visprog.model.GeneralResponseModel
//import com.example.alp_visprog.model.GetAllTodoResponse
//import com.example.alp_visprog.model.GetTodoResponse
//import com.example.alp_visprog.model.TodoModel
//import com.example.alp_visprog.model.TodoRequest
import com.example.alp_visprog.model.PenaltyModelItem
import com.example.alp_visprog.model.PenaltyModel

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PenaltyAPIService {
    @GET("api/penalty")
    fun getAllPenalty(@Header("X-API-TOKEN") token: String): Call<PenaltyModel>

    @GET("api/penalty/{id}")
    fun getPenalty(@Header("X-API-TOKEN") token: String, @Path("id") penaltyId: Int): Call<PenaltyModelItem>

    @POST("api/penalty")
    fun createPenalty(@Body penaltyModel: PenaltyModelItem): Call<PenaltyModelItem>

    @PUT("api/penalty/{id}")
    fun updatePenalty(@Header("X-API-TOKEN") token: String, @Path("id") penaltyId: Int, @Body penaltyModel: PenaltyModelItem): Call<PenaltyModelItem>

    @DELETE("api/penalty/{id}")
    fun deletePenalty(@Header("X-API-TOKEN") token: String, @Path("id") penaltyId: Int): Call<Void>


}