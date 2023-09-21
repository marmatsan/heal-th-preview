package com.marmatsan.onboarding_ui.age

sealed class AgeEvent {
    data class OnAgeEnter(val age: String) : AgeEvent()
    object OnNextClicked : AgeEvent()
    object OnBackClicked : AgeEvent()
}