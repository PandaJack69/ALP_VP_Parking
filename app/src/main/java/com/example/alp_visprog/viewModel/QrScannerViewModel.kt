package com.example.alp_visprog.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alp_visprog.repository.ReservationRepository
import kotlinx.coroutines.launch

class QrScannerViewModel(
    private val reservationRepository: ReservationRepository // Inject the repository
) : ViewModel() {
    fun updateReservationStatus(reservationId: Int, status: String) {
        viewModelScope.launch {
            try {
                // Update the reservation status in the database
                // Make sure reservationId is passed as an Int
                reservationRepository.updateStatus(reservationId, status)
                println("Reservation for reservationId: $reservationId updated to '$status'")
            } catch (e: Exception) {
                println("Error updating reservation: ${e.message}")
            }
        }
    }
}