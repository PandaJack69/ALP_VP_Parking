package com.lia.alp_vp_2.repository

import com.lia.alp_vp_2.model.ReportModel
import com.lia.alp_vp_2.service.ReportService

class ReportRepository(private val reportService: ReportService) {
    suspend fun getReports(token: String): List<ReportModel> {
        return reportService.getReports(token)
    }

    suspend fun createReport(token: String, reportModel: ReportModel) {
        return reportService.createReport(token, reportModel)
    }
}

