package com.lia.alp_vp_2.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lia.alp_vp_2.model.ReservationModel
import com.lia.alp_vp_2.repository.ReservationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ReservationViewModel(
    private val reservationRepository: ReservationRepository
) : ViewModel() {
    val reservations = MutableStateFlow<List<ReservationModel>>(emptyList())

    fun fetchUserReservations(userId: Int) {
        viewModelScope.launch {
            val response = reservationRepository.getUserReservations(userId)
            if (response.isSuccessful) {
                reservations.value = response.body() ?: emptyList()
            }
        }
    }
}

