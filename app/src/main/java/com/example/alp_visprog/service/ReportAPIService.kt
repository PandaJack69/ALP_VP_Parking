package com.example.alp_visprog.service

import com.example.alp_visprog.model.ReportModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ReportAPIService {
    @GET("reports")
    suspend fun getReports(@Header("Authorization") token: String): List<ReportModel>

    @POST("reports")
    suspend fun createReport(
        @Header("Authorization") token: String,
        @Body reportModel: ReportModel
    )
}
