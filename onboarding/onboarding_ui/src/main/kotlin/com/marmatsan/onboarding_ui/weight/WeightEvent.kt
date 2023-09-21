package com.marmatsan.onboarding_ui.weight

sealed class WeightEvent {
    data class OnWeightEnter(val weight: String) : WeightEvent()
    object OnNextClicked : WeightEvent()
    object OnBackClicked : WeightEvent()
}