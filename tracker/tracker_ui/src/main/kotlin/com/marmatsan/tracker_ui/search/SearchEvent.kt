package com.marmatsan.tracker_ui.search

import com.marmatsan.tracker_domain.model.Meal
import com.marmatsan.tracker_domain.model.TrackableFood
import java.time.LocalDate

sealed class SearchEvent {
    data class OnQueryChange(
        val query: String
    ) : SearchEvent()

    object OnSearch : SearchEvent()

    data class OnToggleTrackableFood(
        val food: TrackableFood
    ) : SearchEvent()

    data class OnAmountForFoodChange(
        val food: TrackableFood,
        val amount: String
    ) : SearchEvent()

    data class OnTrackFoodClick(
        val food: TrackableFood,
        val meal: Meal,
        val date: LocalDate
    ) : SearchEvent()

    data class OnSearchFocusChange(val isFocused: Boolean) : SearchEvent()
}