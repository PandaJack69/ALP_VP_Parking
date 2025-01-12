package com.lia.alp_vp_2.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lia.alp_vp_2.model.ProfileModel
import com.lia.alp_vp_2.repository.ProfileRepository
import com.lia.alp_vp_2.uiState.ProfileUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: ProfileRepository) : ViewModel() {

    private val _profileUiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val profileUiState: StateFlow<ProfileUiState> = _profileUiState

    init {
        fetchProfile()
    }

    private fun fetchProfile() {
        viewModelScope.launch {
            try {
                val profile = repository.getProfile()
                _profileUiState.value = ProfileUiState.Success(profile)
            } catch (e: Exception) {
                _profileUiState.value = ProfileUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun updateProfile(NIM: String, updatedProfile: ProfileModel) {
        viewModelScope.launch {
            try {
                val updated = repository.updateProfile(NIM, updatedProfile)
                _profileUiState.value = ProfileUiState.Success(updated)
            } catch (e: Exception) {
                _profileUiState.value = ProfileUiState.Error(e.message ?: "Failed to update profile")
            }
        }
    }
}

