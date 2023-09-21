package com.marmatsan.tracker_domain.use_case

import com.marmatsan.tracker_domain.model.Meal
import com.marmatsan.tracker_domain.model.Meal.MealType
import com.marmatsan.tracker_domain.model.TrackableFood
import com.marmatsan.tracker_domain.model.TrackedFood
import com.marmatsan.tracker_domain.repository.RequestState
import com.marmatsan.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import java.time.LocalDate
import kotlin.math.roundToInt

class TrackFood(
    private val repository: TrackerRepository
) {

    suspend operator fun invoke(
        food: TrackableFood,
        amount: Int,
        meal: Meal,
        date: LocalDate
    ) {
        repository.insertTrackedFood(
            TrackedFood(
                name = food.name,
                carbs = ((food.carbsPer100g / 100f) * amount).roundToInt(),
                protein = ((food.proteinPer100g / 100f) * amount).roundToInt(),
                fat = ((food.fatPer100g / 100f) * amount).roundToInt(),
                calories = ((food.caloriesPer100g / 100f) * amount).roundToInt(),
                imageUrl = food.imageUrl,
                meal = meal,
                amount = amount,
                date = date
            )
        )
    }
}