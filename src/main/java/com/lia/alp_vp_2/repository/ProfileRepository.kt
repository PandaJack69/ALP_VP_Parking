package com.lia.alp_vp_2.repository

import com.lia.alp_vp_2.model.ProfileModel
import com.lia.alp_vp_2.service.ProfileService

class ProfileRepository(private val profileService: ProfileService) {

    suspend fun getProfile(): ProfileModel {
        return profileService.getProfile()
    }

    suspend fun updateProfile(profileId: String, updatedProfile: ProfileModel): ProfileModel {
        return profileService.updateProfile(profileId, updatedProfile)
    }
}

