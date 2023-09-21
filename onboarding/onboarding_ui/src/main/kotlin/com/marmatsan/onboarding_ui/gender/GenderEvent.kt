package com.marmatsan.onboarding_ui.gender

import com.marmatsan.core_domain.model.Gender

sealed class GenderEvent {
    data class OnGenderEnter(val gender: Gender) : GenderEvent()
    object OnNextClicked : GenderEvent()
}