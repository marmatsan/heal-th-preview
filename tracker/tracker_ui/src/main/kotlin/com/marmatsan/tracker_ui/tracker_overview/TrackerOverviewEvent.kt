package com.marmatsan.tracker_ui.tracker_overview

import com.marmatsan.tracker_domain.model.TrackedFood

sealed class TrackerOverviewEvent {
    data class OnToggleMealClick(val mealItem: MealItem) : TrackerOverviewEvent()
    data class OnAddFoodClick(val mealName: String) : TrackerOverviewEvent()
    object OnNextDayClick : TrackerOverviewEvent()
    object OnPreviousDayClick : TrackerOverviewEvent()
    data class OnDeleteTrackedFoodClick(val trackedFood: TrackedFood) : TrackerOverviewEvent()
}