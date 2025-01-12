package com.lia.alp_vp_2.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.lia.alp_vp_2.repository.AuthenticationRepository
import com.lia.alp_vp_2.uiState.AuthenticationResult
import com.lia.alp_vp_2.uiState.AuthenticationUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import androidx.compose.runtime.collectAsState


class AuthenticationViewModel(
    private val repository: AuthenticationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthenticationUiState())
    val uiState: StateFlow<AuthenticationUiState> = _uiState


    private val _authResult = MutableSharedFlow<AuthenticationResult>()
    val authResult = _authResult.asSharedFlow()

    fun updateField(field: String, value: String) {
        _uiState.update { currentState ->
            when (field) {
                "firstName" -> currentState.copy(firstName = value)
                "lastName" -> currentState.copy(lastName = value)
                "nim" -> currentState.copy(nim = value)
                "licensePlate" -> currentState.copy(licensePlate = value)
                "email" -> currentState.copy(email = value)
                "password" -> currentState.copy(password = value)
                else -> currentState
            }
        }
    }

    fun updateSimImage(uri: Uri?) {
        _uiState.update { it.copy(simImageUri = uri) }
    }

    fun signUp() {
        viewModelScope.launch {
            val currentState = _uiState.value

            if (!validateSignUpFields(currentState)) {
                return@launch
            }

            _authResult.emit(AuthenticationResult.Loading)
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                val simImage = currentState.simImageUri?.let { uri ->
                    repository.uploadImage(uri).getOrThrow()
                } ?: throw IllegalStateException("SIM image is required")

                val request = mapOf(
                    "firstName" to currentState.firstName,
                    "lastName" to currentState.lastName,
                    "nim" to currentState.nim,
                    "licensePlate" to currentState.licensePlate,
                    "simImage" to simImage.absolutePath,
                    "email" to currentState.email,
                    "password" to currentState.password
                )

                val result = repository.signUp(request)
                if (result.isSuccess) {
                    _authResult.emit(AuthenticationResult.Success)
                    _uiState.update { it.copy(isAuthenticated = true) }
                } else {
                    _authResult.emit(AuthenticationResult.Error(result.exceptionOrNull()?.message ?: "Sign up failed"))
                }
            } catch (e: Exception) {
                _authResult.emit(AuthenticationResult.Error(e.message ?: "Sign up failed"))
                _uiState.update { it.copy(error = e.message) }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun login() {
        viewModelScope.launch {
            val currentState = _uiState.value

            if (!validateLoginFields(currentState)) {
                return@launch
            }

            _authResult.emit(AuthenticationResult.Loading)
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                val request = mapOf(
                    "email" to currentState.email,
                    "password" to currentState.password
                )

                val result = repository.login(request)
                if (result.isSuccess) {
                    _authResult.emit(AuthenticationResult.Success)
                    _uiState.update { it.copy(isAuthenticated = true) }
                } else {
                    _authResult.emit(AuthenticationResult.Error(result.exceptionOrNull()?.message ?: "Login failed"))
                }
            } catch (e: Exception) {
                _authResult.emit(AuthenticationResult.Error(e.message ?: "Login failed"))
                _uiState.update { it.copy(error = e.message) }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun validateSignUpFields(state: AuthenticationUiState): Boolean {
        if (state.firstName.isBlank() ||
            state.lastName.isBlank() ||
            state.nim.isBlank() ||
            state.licensePlate.isBlank() ||
            state.simImageUri == null ||
            state.email.isBlank() ||
            state.password.isBlank()
        ) {
            _uiState.update { it.copy(error = "All fields are required") }
            return false
        }
        return true
    }

    private fun validateLoginFields(state: AuthenticationUiState): Boolean {
        if (state.email.isBlank() || state.password.isBlank()) {
            _uiState.update { it.copy(error = "Email and password are required") }
            return false
        }
        return true
    }

    class Factory(private val repository: AuthenticationRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AuthenticationViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AuthenticationViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

