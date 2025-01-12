package com.lia.alp_vp_2.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lia.alp_vp_2.model.PenaltyModelItem
import com.lia.alp_vp_2.repository.PenaltyRepository
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