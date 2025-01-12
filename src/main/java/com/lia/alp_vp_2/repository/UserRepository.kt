package com.lia.alp_vp_2.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.lia.alp_vp_2.model.GeneralResponseModel
import com.lia.alp_vp_2.model.UserModel
import com.lia.alp_vp_2.model.UserResponse
import com.lia.alp_vp_2.service.UserAPIService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException

class NetworkUserRepository(
    private val userDataStore: DataStore<Preferences>,
    private val userAPIService: UserAPIService
) : UserRepository {

    private object PreferencesKeys {
        val USER_TOKEN = stringPreferencesKey("user_token")
        val USERNAME = stringPreferencesKey("username")
    }

    override suspend fun getUserProfile(): Result<UserModel> {
        return try {
            val response = userAPIService.getUserProfile()
            Result.success(response.data)
        } catch (e: HttpException) {
            Result.failure(Exception("Failed to get user profile: ${e.message()}"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateUserProfile(updatedUser: Map<String, String>): Result<UserModel> {
        return try {
            val response = userAPIService.updateUserProfile(updatedUser)
            Result.success(response.data)
        } catch (e: HttpException) {
            Result.failure(Exception("Failed to update user profile: ${e.message()}"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun saveUserToken(token: String) {
        userDataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_TOKEN] = token
        }
    }

    override fun getUserToken(): Flow<String?> {
        return userDataStore.data.map { preferences ->
            preferences[PreferencesKeys.USER_TOKEN]
        }
    }

    override suspend fun clearUserToken() {
        userDataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.USER_TOKEN)
        }
    }

    override suspend fun saveUsername(username: String) {
        userDataStore.edit { preferences ->
            preferences[PreferencesKeys.USERNAME] = username
        }
    }

    override fun getUsername(): Flow<String?> {
        return userDataStore.data.map { preferences ->
            preferences[PreferencesKeys.USERNAME]
        }
    }

    override suspend fun logout(token: String) = userAPIService.logout(token)
}

interface UserRepository {
    suspend fun getUserProfile(): Result<UserModel>
    suspend fun updateUserProfile(updatedUser: Map<String, String>): Result<UserModel>
    suspend fun saveUserToken(token: String)
    fun getUserToken(): Flow<String?>
    suspend fun clearUserToken()
    suspend fun saveUsername(username: String)
    fun getUsername(): Flow<String?>
    suspend fun logout(token: String): retrofit2.Call<GeneralResponseModel>
}

