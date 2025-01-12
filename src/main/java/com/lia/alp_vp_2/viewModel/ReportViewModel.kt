package com.lia.alp_vp_2.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lia.alp_vp_2.model.ReportModel
import com.lia.alp_vp_2.repository.ReportRepository
import com.lia.alp_vp_2.uiState.ReportUIState
import kotlinx.coroutines.launch

class ReportViewModel(private val reportRepository: ReportRepository) : ViewModel() {

    var reportState: ReportUIState = ReportUIState.Start
        private set

    fun getReports(token: String) {
        viewModelScope.launch {
            reportState = ReportUIState.Loading
            try {
                val reports = reportRepository.getReports(token)
                reportState = ReportUIState.Success(reports.map { it.description })
            } catch (e: Exception) {
                reportState = ReportUIState.Failed("Error fetching reports: ${e.localizedMessage}")
            }
        }
    }

    fun createReport(token: String, reportModel: ReportModel) {
        viewModelScope.launch {
            reportState = ReportUIState.Loading
            try {
                reportRepository.createReport(token, reportModel)
                reportState = ReportUIState.Success(listOf("Report created successfully"))
            } catch (e: Exception) {
                reportState = ReportUIState.Failed("Error creating report: ${e.localizedMessage}")
            }
        }
    }
}

