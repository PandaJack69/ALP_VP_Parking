package com.example.alp_visprog.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import com.example.alp_visprog.ParkingApplication
import com.example.alp_visprog.enum.PagesEnum
import com.example.alp_visprog.enum.PrioritiesEnum
import com.example.alp_visprog.model.ErrorModel
import com.example.alp_visprog.model.GeneralResponseModel
import com.example.alp_visprog.model.ReservationModel
import com.example.alp_visprog.model.ParkingLotModel
import com.example.alp_visprog.repository.UserRepository
import com.example.alp_visprog.uiState.AuthenticationUIState
import com.example.alp_visprog.uiState.HomeUIState
import com.example.alp_visprog.uiState.StringDataStatusUIState
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.Date

class HomeViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {
    private val _homeUIState = MutableStateFlow(HomeUIState())

    private val _reservationState = MutableStateFlow(ReservationState())
    val reservationState: StateFlow<ReservationState> = _reservationState

    val userId: StateFlow<String> = userRepository.currentUserId.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = "Unknown"
    )

//    val userId: String = userRepository.getUserId() // Fetch the user ID from the repository

//    init {
//        fetchActiveReservation(userId)
//    }

    init {
        // Observe userId and fetch reservation when it changes
        viewModelScope.launch {
            userRepository.currentUserId.collect { userId ->
                if (userId != "Unknown") {
                    fetchActiveReservation(userId)
                }
            }
        }
    }

    private fun fetchActiveReservation(userId: String) {
        viewModelScope.launch {
            try {
                val activeReservationCall = userRepository.getActiveReservation(userId)
                activeReservationCall.enqueue(object : Callback<ReservationModel> {
                    override fun onResponse(
                        call: Call<ReservationModel>,
                        response: Response<ReservationModel>
                    ) {
                        if (response.isSuccessful) {
                            val activeReservation = response.body()
                            if (activeReservation?.reservationStatus == "active") {
                                _reservationState.value = ReservationState(
                                    hasActiveReservation = true,
                                    activeReservation = activeReservation
                                )
                            } else {
                                _reservationState.value = ReservationState(
                                    hasActiveReservation = false
                                )
                            }
                        } else {
                            Log.e("Reservation", "Failed to fetch reservation: ${response.errorBody()}")
                        }
                    }

                    override fun onFailure(call: Call<ReservationModel>, t: Throwable) {
                        Log.e("Reservation", "Error fetching reservation: ${t.localizedMessage}")
                    }
                })
            } catch (e: IOException) {
                Log.e("Reservation", "IOException: ${e.localizedMessage}")
            }
        }
    }

    data class ReservationState(
        val hasActiveReservation: Boolean = false,
        val activeReservation: ReservationModel? = null
    )

    var logoutStatus: StringDataStatusUIState by mutableStateOf(StringDataStatusUIState.Start)
        private set

    val username: StateFlow<String> = userRepository.currentUsername.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ""
    )

    val token: StateFlow<String> = userRepository.currentUserToken.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ""
    )

    fun clearDialog() {
        _homeUIState.update { state ->
            state.copy(showDialog = false)
        }
    }

    fun changePriorityTextBackgroundColor(
        priority: PrioritiesEnum
    ): Color {
        return when (priority) {
            PrioritiesEnum.High -> Color.Red
            PrioritiesEnum.Medium -> Color.Yellow
            else -> Color.Green
        }
    }

    fun logoutUser(token: String, navController: NavHostController) {
        viewModelScope.launch {
            logoutStatus = StringDataStatusUIState.Loading
            try {
                val call = userRepository.logout(token)
                call.enqueue(object : Callback<GeneralResponseModel> {
                    override fun onResponse(call: Call<GeneralResponseModel>, res: Response<GeneralResponseModel>) {
                        if (res.isSuccessful) {
                            logoutStatus = StringDataStatusUIState.Success(data = res.body()!!.data)
                            saveUsernameToken("Unknown", "Unknown")
                            navController.navigate(PagesEnum.Login.name) {
                                popUpTo(PagesEnum.Home.name) { inclusive = true }
                            }
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )
                            logoutStatus = StringDataStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<GeneralResponseModel>, t: Throwable) {
                        logoutStatus = StringDataStatusUIState.Failed(t.localizedMessage)
                        Log.e("logout-failure", t.localizedMessage)
                    }
                })
            } catch (error: IOException) {
                logoutStatus = StringDataStatusUIState.Failed(error.localizedMessage)
                Log.e("logout-error", error.localizedMessage)
            }
        }
    }

    fun saveUsernameToken(token: String, username: String) {
        viewModelScope.launch {
            userRepository.saveUserToken(token)
            userRepository.saveUsername(username)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                    ?: throw IllegalStateException("Application is null in ViewModel Factory")
                if (application !is ParkingApplication) {
                    throw IllegalStateException("Expected application of type ParkingApplication, but got ${application.javaClass.name}")
                }
                val userRepository = application.container.userRepository
                HomeViewModel(userRepository)
            }
        }
    }
}
