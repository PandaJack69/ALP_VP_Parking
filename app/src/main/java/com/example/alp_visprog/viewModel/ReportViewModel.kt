package com.example.alp_visprog.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.alp_visprog.ParkingApplication
import com.example.alp_visprog.model.ReportModel
import com.example.alp_visprog.repository.ReportRepository
import com.example.alp_visprog.uiState.ReportUIState
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

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as ParkingApplication
                    ?: throw IllegalStateException("Application is null or not ParkingApplication in ViewModel Factory")

                val reportRepository = application.container.reportRepository
                ReportViewModel(reportRepository)
            }
        }
    }
}
