package com.marmatsan.tracker_domain.model

import com.marmatsan.tracker_domain.model.Meal.MealType.BREAKFAST
import com.marmatsan.tracker_domain.model.Meal.MealType.DINNER
import com.marmatsan.tracker_domain.model.Meal.MealType.LUNCH
import com.marmatsan.tracker_domain.model.Meal.MealType.SNACK

sealed class Meal(val mealType: MealType) {

    enum class MealType {
        BREAKFAST,
        LUNCH,
        DINNER,
        SNACK
    }

    object Breakfast : Meal(BREAKFAST)
    object Lunch : Meal(LUNCH)
    object Dinner : Meal(DINNER)
    object Snack : Meal(SNACK)

    companion object {

        fun fromType(mealType: MealType): Meal {
            return when (mealType) {
                BREAKFAST -> Breakfast
                LUNCH -> Lunch
                DINNER -> Dinner
                SNACK -> Snack
            }
        }
    }
}
