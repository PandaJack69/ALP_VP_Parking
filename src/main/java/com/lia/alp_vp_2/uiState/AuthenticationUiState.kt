package com.lia.alp_vp_2.uiState

import android.net.Uri

data class AuthenticationUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isAuthenticated: Boolean = false,
    val firstName: String = "",
    val lastName: String = "",
    val nim: String = "",
    val licensePlate: String = "",
    val simImageUri: Uri? = null,
    val email: String = "",
    val password: String = ""
)

