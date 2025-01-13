package com.example.alp_visprog.repository

import com.example.alp_visprog.model.ProfileModel
import com.example.alp_visprog.service.ProfileAPIService
//import com.example.alp_visprog.service.ProfileService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface ProfileRepository {
    suspend fun getProfile(): ProfileModel
    suspend fun updateProfile(profileId: String, updatedProfile: ProfileModel): ProfileModel
}

class NetworkProfileRepository(
    private val profileAPIService: ProfileAPIService
) : ProfileRepository {

    override suspend fun getProfile(): ProfileModel {
        return withContext(Dispatchers.IO) {
            profileAPIService.getProfile() // Perform the network operation on the IO dispatcher
        }
//        return profileAPIService.getProfile()
    }

    override suspend fun updateProfile(profileId: String, updatedProfile: ProfileModel): ProfileModel {
        return withContext(Dispatchers.IO) {
            profileAPIService.updateProfile(profileId, updatedProfile) // Perform the network operation on the IO dispatcher
        }
//        return profileAPIService.updateProfile(profileId, updatedProfile)
    }
}
