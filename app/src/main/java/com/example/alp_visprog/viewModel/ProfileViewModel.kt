package com.example.alp_visprog.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.alp_visprog.ParkingApplication
import com.example.alp_visprog.model.ProfileModel
import com.example.alp_visprog.repository.ProfileRepository
import com.example.alp_visprog.uiState.ProfileUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _profileUiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val profileUiState: StateFlow<ProfileUiState> = _profileUiState

    init {
        fetchProfile()
    }

    private fun fetchProfile() {
        viewModelScope.launch {
            try {
                val profile = profileRepository.getProfile()
                _profileUiState.value = ProfileUiState.Success(profile)
            } catch (e: Exception) {
                _profileUiState.value = ProfileUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun updateProfile(NIM: String, updatedProfile: ProfileModel) {
        viewModelScope.launch {
            try {
                val updated = profileRepository.updateProfile(NIM, updatedProfile)
                _profileUiState.value = ProfileUiState.Success(updated)
            } catch (e: Exception) {
                _profileUiState.value = ProfileUiState.Error(e.message ?: "Failed to update profile")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as ParkingApplication
                    ?: throw IllegalStateException("Application is null or not ParkingApplication in ViewModel Factory")

                val profileRepository = application.container.profileRepository
                ProfileViewModel(profileRepository)
            }
        }
    }
}
