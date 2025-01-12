package com.lia.alp_vp_2.uiState

sealed class AuthenticationResult {
    object Success : AuthenticationResult()
    data class Error(val message: String) : AuthenticationResult()
    object Loading : AuthenticationResult()
}