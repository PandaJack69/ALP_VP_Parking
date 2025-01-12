package com.lia.alp_vp_2.uiState

sealed interface ReportUIState {
    object Start : ReportUIState
    object Loading : ReportUIState
    data class Success(val data: List<String>) : ReportUIState
    data class Failed(val error: String) : ReportUIState
}