package com.marmatsan.core_domain.preferences

import com.marmatsan.core_domain.model.UserInfo
import kotlinx.serialization.Serializable

@Serializable
data class PreferencesData(
    val userInfo: UserInfo = UserInfo(),
    @Serializable
    val showOnboarding: Boolean = true
)
