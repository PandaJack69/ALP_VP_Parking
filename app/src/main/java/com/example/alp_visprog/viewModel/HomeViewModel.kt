package com.example.alp_visprog.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import com.example.alp_visprog.ParkingApplication
import com.example.alp_visprog.enum.PagesEnum
import com.example.alp_visprog.enum.PrioritiesEnum
import com.example.alp_visprog.model.ErrorModel
import com.example.alp_visprog.model.GeneralResponseModel
//import com.example.alp_visprog.model.GetAllTodoResponse
//import com.example.alp_visprog.repository.TodoRepository
import com.example.alp_visprog.repository.UserRepository
import com.example.alp_visprog.uiState.AuthenticationUIState
import com.example.alp_visprog.uiState.HomeUIState
import com.example.alp_visprog.uiState.StringDataStatusUIState
//import com.example.alp_visprog.uiState.TodoDataStatusUIState
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class HomeViewModel(
    private val userRepository: UserRepository,
//    private val todoRepository: TodoRepository
): ViewModel() {
    private val _homeUIState = MutableStateFlow(HomeUIState())

    var logoutStatus: StringDataStatusUIState by mutableStateOf(StringDataStatusUIState.Start)
        private set

//    var dataStatus: TodoDataStatusUIState by mutableStateOf(TodoDataStatusUIState.Start)
//        private set

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
            state.copy(
                showDialog = false
            )
        }
    }

    fun changePriorityTextBackgroundColor(
        priority: PrioritiesEnum
    ): Color {
        if (priority == PrioritiesEnum.High) {
            return Color.Red
        } else if (priority == PrioritiesEnum.Medium) {
            return Color.Yellow
        }

        return Color.Green
    }

    fun logoutUser(token: String, navController: NavHostController) {
        viewModelScope.launch {
            logoutStatus = StringDataStatusUIState.Loading

            Log.d("token-logout", "LOGOUT TOKEN: ${token}")

            try {
                val call = userRepository.logout(token)

                call.enqueue(object: Callback<GeneralResponseModel>{
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
                            // set error message toast
                        }
                    }

                    override fun onFailure(call: Call<GeneralResponseModel>, t: Throwable) {
                        logoutStatus = StringDataStatusUIState.Failed(t.localizedMessage)
                        Log.d("logout-failure", t.localizedMessage)
                    }
                })
            } catch (error: IOException) {
                logoutStatus = StringDataStatusUIState.Failed(error.localizedMessage)
                Log.d("logout-error", error.localizedMessage)
            }
        }
    }

//    fun getAllTodos(token: String) {
//        viewModelScope.launch {
//            Log.d("token-home", "TOKEN AT HOME: ${token}")
//
//            dataStatus = TodoDataStatusUIState.Loading
//
//            try {
//                val call = todoRepository.getAllTodos(token)
//                call.enqueue(object : Callback<GetAllTodoResponse> {
//                    override fun onResponse(call: Call<GetAllTodoResponse>, res: Response<GetAllTodoResponse>) {
//                        if (res.isSuccessful) {
//                            dataStatus = TodoDataStatusUIState.Success(res.body()!!.data)
//
//                            Log.d("data-result", "TODO LIST DATA: ${dataStatus}")
//                        } else {
//                            val errorMessage = Gson().fromJson(
//                                res.errorBody()!!.charStream(),
//                                ErrorModel::class.java
//                            )
//
//                            dataStatus = TodoDataStatusUIState.Failed(errorMessage.errors)
//                        }
//                    }
//
//                    override fun onFailure(call: Call<GetAllTodoResponse>, t: Throwable) {
//                        dataStatus = TodoDataStatusUIState.Failed(t.localizedMessage)
//                    }
//
//                })
//            } catch (error: IOException) {
//                dataStatus = TodoDataStatusUIState.Failed(error.localizedMessage)
//            }
//        }
//    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ParkingApplication)
                val userRepository = application.container.userRepository
//                val todoRepository = application.container.todoRepository
                HomeViewModel(
                    userRepository,
//                    todoRepository
                )
            }
        }
    }

    fun convertStringToEnum(text: String): PrioritiesEnum {
        if (text == "High") {
            return PrioritiesEnum.High
        } else if (text == "Medium") {
            return PrioritiesEnum.Medium
        } else {
            return PrioritiesEnum.Low
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

//    fun clearDataErrorMessage() {
//        dataStatus = TodoDataStatusUIState.Start
//    }
}