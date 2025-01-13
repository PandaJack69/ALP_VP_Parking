package com.example.alp_visprog.repository

import com.example.alp_visprog.model.ReportModel
import com.example.alp_visprog.service.ReportAPIService
import com.example.alp_visprog.model.ProfileModel
//import com.example.alp_visprog.service.ProfileService

interface ReportRepository {
    suspend fun getReports(token: String): List<ReportModel>
    suspend fun createReport(token: String, reportModel: ReportModel)
}

class NetworkReportRepository(
    private val reportAPIService: ReportAPIService
) : ReportRepository {

    override suspend fun getReports(token: String): List<ReportModel> {
        return reportAPIService.getReports(token)
    }

    override suspend fun createReport(token: String, reportModel: ReportModel) {
        return reportAPIService.createReport(token, reportModel)
    }
}

