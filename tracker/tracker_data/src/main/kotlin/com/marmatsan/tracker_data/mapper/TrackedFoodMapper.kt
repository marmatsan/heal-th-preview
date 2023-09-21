package com.marmatsan.tracker_data.mapper

import com.marmatsan.tracker_data.local.entity.TrackedFoodEntity
import com.marmatsan.tracker_domain.model.Meal
import com.marmatsan.tracker_domain.model.TrackedFood
import java.time.LocalDate

// TODO: Change of LocalDate to a more robust type
fun TrackedFoodEntity.toTrackedFood(): TrackedFood {
    return TrackedFood(
        id = id,
        name = name,
        carbs = carbs,
        protein = protein,
        fat = fat,
        imageUrl = imageUrl,
        meal = Meal.fromType(mealType),
        amount = amount,
        date = LocalDate.of(year, month, dayOfMonth),
        calories = calories
    )
}

fun TrackedFood.toTrackedFoodEntity(): TrackedFoodEntity {
    return TrackedFoodEntity(
        id = id,
        name = name,
        carbs = carbs,
        protein = protein,
        fat = fat,
        imageUrl = imageUrl,
        mealType = meal.mealType,
        amount = amount,
        dayOfMonth = date.dayOfMonth,
        month = date.monthValue,
        year = date.year,
        calories = calories
    )
}