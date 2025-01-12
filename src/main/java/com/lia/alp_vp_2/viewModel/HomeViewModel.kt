package com.lia.alp_vp_2.viewModel

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
import com.lia.alp_vp_2.ParkingApplication
import com.lia.alp_vp_2.enum.PagesEnum
import com.lia.alp_vp_2.enum.PrioritiesEnum
import com.lia.alp_vp_2.model.ErrorModel
import com.lia.alp_vp_2.model.GeneralResponseModel
import com.lia.alp_vp_2.repository.UserRepository
import com.lia.alp_vp_2.uiState.HomeUIState
import com.lia.alp_vp_2.uiState.StringDataStatusUIState
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

class HomeViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _homeUIState = MutableStateFlow(HomeUIState())

    var logoutStatus: StringDataStatusUIState by mutableStateOf(StringDataStatusUIState.Start)
        private set

    val username: StateFlow<String> = userRepository.getUsername().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ""
    )

    val token: StateFlow<String> = userRepository.getUserToken().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ""
    )

    fun clearDialog() {
        _homeUIState.update { state ->
            state.copy(
                showDialog = false
            )
        }
    }

    fun changePriorityTextBackgroundColor(
        priority: PrioritiesEnum
    ): Color {
        return when (priority) {
            PrioritiesEnum.High -> Color.Red
            PrioritiesEnum.Medium -> Color.Yellow
            PrioritiesEnum.Low -> Color.Green
        }
    }

    fun logoutUser(token: String, navController: NavHostController) {
        viewModelScope.launch {
            logoutStatus = StringDataStatusUIState.Loading

            Log.d("token-logout", "LOGOUT TOKEN: $token")

            try {
                val call = userRepository.logout(token)

                call.enqueue(object : Callback<GeneralResponseModel> {
                    override fun onResponse(call: Call<GeneralResponseModel>, res: Response<GeneralResponseModel>) {
                        if (res.isSuccessful) {
                            logoutStatus = StringDataStatusUIState.Success(data = res.body()!!.data)

                            saveUsernameToken("Unknown", "Unknown")

                            navController.navigate(PagesEnum.Login.name) {
                                popUpTo(PagesEnum.Home.name) {
                                    inclusive = true
                                }
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
                        logoutStatus = StringDataStatusUIState.Failed(t.localizedMessage ?: "Unknown error")
                        Log.d("logout-failure", t.localizedMessage ?: "Unknown error")
                    }
                })
            } catch (error: IOException) {
                logoutStatus = StringDataStatusUIState.Failed(error.localizedMessage ?: "Unknown error")
                Log.d("logout-error", error.localizedMessage ?: "Unknown error")
            }
        }
    }

    fun convertStringToEnum(text: String): PrioritiesEnum {
        return when (text) {
            "High" -> PrioritiesEnum.High
            "Medium" -> PrioritiesEnum.Medium
            else -> PrioritiesEnum.Low
        }
    }

    fun saveUsernameToken(token: String, username: String) {
        viewModelScope.launch {
            userRepository.saveUserToken(token)
            userRepository.saveUsername(username)
        }
    }

    fun clearLogoutErrorMessage() {
        logoutStatus = StringDataStatusUIState.Start
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ParkingApplication)
                val userRepository = application.container.userRepository
                HomeViewModel(userRepository)
            }
        }
    }
}

