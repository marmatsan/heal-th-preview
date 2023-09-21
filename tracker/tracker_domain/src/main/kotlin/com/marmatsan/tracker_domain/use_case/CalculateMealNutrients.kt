package com.marmatsan.tracker_domain.use_case

import com.marmatsan.core_domain.model.ActivityLevel.High
import com.marmatsan.core_domain.model.ActivityLevel.Low
import com.marmatsan.core_domain.model.ActivityLevel.Medium
import com.marmatsan.core_domain.model.Gender.Female
import com.marmatsan.core_domain.model.Gender.Male
import com.marmatsan.core_domain.model.Gender.Unknown
import com.marmatsan.core_domain.model.UserInfo
import com.marmatsan.core_domain.model.WeightGoal.GainWeight
import com.marmatsan.core_domain.model.WeightGoal.KeepWeight
import com.marmatsan.core_domain.model.WeightGoal.LoseWeight
import com.marmatsan.core_domain.preferences.Preferences
import com.marmatsan.tracker_domain.model.Meal
import com.marmatsan.tracker_domain.model.TrackedFood
import kotlinx.coroutines.flow.single
import kotlin.math.roundToInt

class CalculateMealNutrients(
    private val preferences: Preferences
) {

    suspend operator fun invoke(trackedFoods: List<TrackedFood>): Result {
        val allNutrients = trackedFoods
            .groupBy { it.meal }
            .mapValues { entry ->
                val meal = entry.key
                val foods = entry.value
                MealNutrients(
                    carbs = foods.sumOf { it.carbs },
                    protein = foods.sumOf { it.protein },
                    fat = foods.sumOf { it.fat },
                    calories = foods.sumOf { it.calories },
                    meal = meal
                )
            }

        val totalCarbs = allNutrients.values.sumOf { it.carbs }
        val totalProtein = allNutrients.values.sumOf { it.protein }
        val totalFat = allNutrients.values.sumOf { it.fat }
        val totalCalories = allNutrients.values.sumOf { it.calories }

        val preferencesData = preferences.loadPreferencesData().single()

        val userInfo = preferencesData.userInfo

        // TODO: Improve logic
        val caloricGoal = dailyCaloricRequirement(userInfo)
        val carbsGoal = (caloricGoal * userInfo.carbRatio!! / 4f).roundToInt()
        val proteinGoal = (caloricGoal * userInfo.proteinRatio!! / 4f).roundToInt()
        val fatGoal = (caloricGoal * userInfo.fatRatio!! / 9f).roundToInt()

        return Result(
            carbsGoal = carbsGoal,
            proteinGoal = proteinGoal,
            fatGoal = fatGoal,
            caloriesGoal = caloricGoal,
            totalCarbs = totalCarbs,
            totalProtein = totalProtein,
            totalFat = totalFat,
            totalCalories = totalCalories,
            mealNutrients = allNutrients
        )
    }

    private fun bmr(userInfo: UserInfo): Int {
        return when (userInfo.gender) {
            Female -> {
                (665.09f + 9.56f * userInfo.weight!! +
                        1.84f * userInfo.height!! - 4.67 * userInfo.age!!).roundToInt()
            }

            Male -> {
                (66.47f + 13.75f * userInfo.weight!! +
                        5f * userInfo.height!! - 6.75f * userInfo.age!!).roundToInt()
            }

            Unknown -> TODO()
            null -> TODO()
        }
    }

    private fun dailyCaloricRequirement(userInfo: UserInfo): Int {
        val activityFactor = when (userInfo.activityLevel) {
            is Low -> 1.2f
            is Medium -> 1.3f
            is High -> 1.4f
            null -> TODO()
        }
        val caloricExtra = when (userInfo.weightGoal) {
            GainWeight -> 500
            KeepWeight -> 0
            LoseWeight -> -500
            null -> TODO()
        }
        return (bmr(userInfo) * activityFactor + caloricExtra).roundToInt()
    }

    data class MealNutrients(
        val carbs: Int,
        val protein: Int,
        val fat: Int,
        val calories: Int,
        val meal: Meal
    )

    data class Result(
        val carbsGoal: Int,
        val proteinGoal: Int,
        val fatGoal: Int,
        val caloriesGoal: Int,
        val totalCarbs: Int,
        val totalProtein: Int,
        val totalFat: Int,
        val totalCalories: Int,
        val mealNutrients: Map<Meal, MealNutrients>
    )
}