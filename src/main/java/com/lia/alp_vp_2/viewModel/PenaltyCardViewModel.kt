package com.lia.alp_vp_2.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lia.alp_vp_2.model.PenaltyModelItem
import com.lia.alp_vp_2.repository.PenaltyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PenaltyCardViewModel(
    private val penaltyRepository: PenaltyRepository
) : ViewModel() {

    private val _penalty = MutableStateFlow(PenaltyModelItem())
    val penalty: StateFlow<PenaltyModelItem> = _penalty

    fun loadPenalty(token: String, penaltyId: Int) {
        viewModelScope.launch {
            val response = penaltyRepository.getPenalty(token, penaltyId).execute()
            if (response.isSuccessful) {
                _penalty.value = response.body() ?: PenaltyModelItem()
            }
        }
    }
}