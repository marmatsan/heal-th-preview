package com.marmatsan.onboarding_ui.height

sealed class HeightEvent {
    data class OnHeightEnter(val height: String) : HeightEvent()
    object OnNextClicked : HeightEvent()
    object OnBackClicked : HeightEvent()
}