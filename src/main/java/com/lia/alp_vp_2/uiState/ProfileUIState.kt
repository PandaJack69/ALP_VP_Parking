package com.lia.alp_vp_2.uiState

import com.lia.alp_vp_2.model.ProfileModel

sealed class ProfileUiState {
    object Loading : ProfileUiState()
    data class Success(val profile: ProfileModel) : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
}
