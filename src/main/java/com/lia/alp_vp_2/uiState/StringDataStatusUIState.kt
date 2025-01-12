package com.lia.alp_vp_2.uiState

sealed interface StringDataStatusUIState {
    data class Success(val message: String) : StringDataStatusUIState
    object Loading : StringDataStatusUIState
    object Start : StringDataStatusUIState
    data class Failed(val errorMessage: String) : StringDataStatusUIState
}