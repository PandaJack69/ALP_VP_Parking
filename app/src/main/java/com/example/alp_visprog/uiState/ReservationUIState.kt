package com.example.alp_visprog.uiState

import com.example.alp_visprog.model.ReservationModel

sealed class ReservationUIState {
    object Loading : ReservationUIState()
    data class Success(val reservations: List<ReservationModel>) : ReservationUIState()
    data class Error(val message: String) : ReservationUIState()
}