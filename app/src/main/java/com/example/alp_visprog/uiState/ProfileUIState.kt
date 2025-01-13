package com.example.alp_visprog.uiState

import com.example.alp_visprog.model.ProfileModel

sealed class ProfileUiState {
    object Loading : ProfileUiState()
    data class Success(val profile: ProfileModel) : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
}