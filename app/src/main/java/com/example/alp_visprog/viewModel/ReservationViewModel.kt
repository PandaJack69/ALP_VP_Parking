package com.example.alp_visprog.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.alp_visprog.ParkingApplication
import com.example.alp_visprog.model.ReservationModel
import com.example.alp_visprog.repository.ReservationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class ReservationViewModel(
    private val reservationRepository: ReservationRepository

) : ViewModel() {
    private val _reservations = MutableStateFlow<List<ReservationModel>>(emptyList())
    val reservations: StateFlow<List<ReservationModel>> get() = _reservations

    private val _hasReservation = MutableStateFlow(false)
    val hasReservation: StateFlow<Boolean> get() = _hasReservation

    private val _reservedSpot = MutableStateFlow<String?>(null)
    val reservedSpot: StateFlow<String?> get() = _reservedSpot

    private val _userId = MutableStateFlow<Int?>(null)
    val userId: StateFlow<Int?> get() = _userId

    private val _licensePlate = MutableStateFlow<String?>(null)
    val licensePlate: StateFlow<String?> get() = _licensePlate

    fun fetchUserReservations(userId: Int) {
        this._userId.value = userId
        viewModelScope.launch {
            val response = reservationRepository.getUserReservations(userId)
            if (response.isSuccessful) {
                val reservationList = response.body() ?: emptyList()
                _reservations.value = reservationList
                _hasReservation.value = reservationList.isNotEmpty()
                _reservedSpot.value = reservationList.firstOrNull()?.let {
                    "${it.parkingLot?.floor}${it.parkingLot?.number}"
                }
//                _licensePlate.value = reservationList.firstOrNull()?.licensePlate // Assuming this is available
            } else {
                // Handle error response
                _reservations.value = emptyList()
                _hasReservation.value = false
                _reservedSpot.value = null
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as ParkingApplication
                    ?: throw IllegalStateException("Application is null or not ParkingApplication in ViewModel Factory")

                val reservationRepository = application.container.reservationRepository
                ReservationViewModel(reservationRepository)
            }
        }
    }
}