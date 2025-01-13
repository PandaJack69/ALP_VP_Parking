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

class PenaltyCardViewModel(
    private val penaltyRepository: PenaltyRepository
) : ViewModel() {

    private val _penalty = MutableStateFlow(PenaltyModelItem()) // Default value
    val penalty: StateFlow<PenaltyModelItem> = _penalty

    fun loadPenalty(token: String, penaltyId: Int) {
        viewModelScope.launch {
            try {
                // Await the result from the suspend function directly
                val penaltyItem = penaltyRepository.getPenalty(token, penaltyId)
                _penalty.value = penaltyItem
            } catch (e: Exception) {
                // Handle errors (e.g., network issues)
                _penalty.value = PenaltyModelItem()  // Default value in case of error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as ParkingApplication
                    ?: throw IllegalStateException("Application is null or not ParkingApplication in ViewModel Factory")

                val penaltyRepository = application.container.penaltyRepository
                PenaltyCardViewModel(penaltyRepository)
            }
        }
    }
}
