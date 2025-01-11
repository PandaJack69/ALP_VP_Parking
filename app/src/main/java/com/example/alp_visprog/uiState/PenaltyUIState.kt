package com.example.alp_visprog.uiState

import com.example.alp_visprog.model.PenaltyModelItem

data class PenaltyUIState(
    val penalties: List<PenaltyModelItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)