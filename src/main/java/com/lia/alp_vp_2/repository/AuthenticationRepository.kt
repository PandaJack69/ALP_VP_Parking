package com.lia.alp_vp_2.repository

import android.content.Context
import android.net.Uri
import com.lia.alp_vp_2.model.UserResponse
import com.lia.alp_vp_2.service.AuthenticationAPIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import retrofit2.HttpException

interface AuthenticationRepository {
    suspend fun signUp(request: Map<String, String>): Result<UserResponse>
    suspend fun login(request: Map<String, String>): Result<UserResponse>
    suspend fun uploadImage(uri: Uri): Result<File>
}

class NetworkAuthenticationRepository(
    private val authenticationAPIService: AuthenticationAPIService,
    private val context: Context
) : AuthenticationRepository {

    override suspend fun signUp(request: Map<String, String>): Result<UserResponse> = withContext(Dispatchers.IO) {
        try {
            val response = authenticationAPIService.register(request)
            Result.success(response)
        } catch (e: HttpException) {
            Result.failure(Exception("Sign up failed: ${e.message()}"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun login(request: Map<String, String>): Result<UserResponse> = withContext(Dispatchers.IO) {
        try {
            val response = authenticationAPIService.login(request)
            Result.success(response)
        } catch (e: HttpException) {
            Result.failure(Exception("Login failed: ${e.message()}"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun uploadImage(uri: Uri): Result<File> = withContext(Dispatchers.IO) {
        try {
            val file = File(context.cacheDir, "sim_image_${System.currentTimeMillis()}.jpg")
            context.contentResolver.openInputStream(uri)?.use { input ->
                file.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            Result.success(file)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

