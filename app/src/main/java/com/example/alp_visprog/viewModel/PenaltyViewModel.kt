package com.example.alp_visprog.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.alp_visprog.ParkingApplication
import com.example.alp_visprog.model.PenaltyModelItem
import com.example.alp_visprog.repository.PenaltyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PenaltyViewModel(
    private val penaltyRepository: PenaltyRepository
) : ViewModel() {

    private val _penalties = MutableStateFlow<List<PenaltyModelItem>>(emptyList())
    val penalties: StateFlow<List<PenaltyModelItem>> = _penalties

    init {
        loadPenalties()
    }

    private fun loadPenalties() {
        viewModelScope.launch {
            try {
                // Simulated token, replace with actual authentication logic
                val token = "user-auth-token"
                val response = penaltyRepository.getAllPenalties(token)

                // Update the penalties list with the response data
                _penalties.value = response
            } catch (e: Exception) {
                // Handle any exceptions or errors
                _penalties.value = emptyList() // You can also set an error state if needed
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as ParkingApplication
                    ?: throw IllegalStateException("Application is null or not ParkingApplication in ViewModel Factory")

                val penaltyRepository = application.container.penaltyRepository
                PenaltyViewModel(penaltyRepository)
            }
        }
    }
}
