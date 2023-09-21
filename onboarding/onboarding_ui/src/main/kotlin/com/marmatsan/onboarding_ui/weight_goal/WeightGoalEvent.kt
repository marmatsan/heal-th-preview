package com.marmatsan.onboarding_ui.weight_goal

import com.marmatsan.core_domain.model.WeightGoal

sealed class WeightGoalEvent {
    data class OnWeightGoalEnter(val weightGoal: WeightGoal) : WeightGoalEvent()
    object OnNextClicked : WeightGoalEvent()
    object OnBackClicked : WeightGoalEvent()
}