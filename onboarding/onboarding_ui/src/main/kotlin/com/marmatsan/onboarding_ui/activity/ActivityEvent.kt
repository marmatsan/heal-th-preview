package com.marmatsan.onboarding_ui.activity

import com.marmatsan.core_domain.model.ActivityLevel

sealed class ActivityEvent {
    data class OnActivityLevelEnter(val activityLevel: ActivityLevel) : ActivityEvent()
    object OnNextClicked : ActivityEvent()
    object OnBackClicked : ActivityEvent()
}