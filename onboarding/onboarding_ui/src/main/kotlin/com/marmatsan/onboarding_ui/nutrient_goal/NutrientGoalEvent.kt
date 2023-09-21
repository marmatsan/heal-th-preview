package com.marmatsan.onboarding_ui.nutrient_goal

sealed class NutrientGoalEvent {
    data class OnCarbPctEnter(val carbPct: String) : NutrientGoalEvent()
    data class OnProteinPctEnter(val proteinPct: String) : NutrientGoalEvent()
    data class OnFatPctEnter(val fatPct: String) : NutrientGoalEvent()
    object OnNextClick : NutrientGoalEvent()
    object OnBackClick : NutrientGoalEvent()
}