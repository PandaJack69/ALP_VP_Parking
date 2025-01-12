package com.lia.alp_vp_2.uiState

import com.lia.alp_vp_2.model.PenaltyModelItem

data class PenaltyUIState(
    val penalties: List<PenaltyModelItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)