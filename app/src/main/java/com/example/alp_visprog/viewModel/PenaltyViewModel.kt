package com.example.alp_visprog.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
            // Simulated token, replace with actual authentication logic
            val token = "user-auth-token"
            val response = penaltyRepository.getAllPenalties(token).execute()
            if (response.isSuccessful) {
                _penalties.value = response.body() ?: emptyList()
            }
        }
    }
}
