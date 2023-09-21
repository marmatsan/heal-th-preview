package com.marmatsan.tracker_ui.tracker_overview

import androidx.annotation.DrawableRes
import com.marmatsan.core_domain.util.UiText
import com.marmatsan.tracker_domain.model.Meal
import com.marmatsan.tracker_ui.R

data class MealItem(
    val name: UiText,
    @DrawableRes
    val drawableRes: Int,
    val meal: Meal,
    val carbs: Int = 0,
    val protein: Int = 0,
    val fat: Int = 0,
    val calories: Int = 0,
    val isExpanded: Boolean = false
)

val defaultMeals = listOf(
    MealItem(
        name = UiText.StringResource(R.string.breakfast),
        drawableRes = R.drawable.ic_breakfast,
        meal = Meal.Breakfast
    ),
    MealItem(
        name = UiText.StringResource(R.string.lunch),
        drawableRes = R.drawable.ic_lunch,
        meal = Meal.Lunch
    ),
    MealItem(
        name = UiText.StringResource(R.string.dinner),
        drawableRes = R.drawable.ic_dinner,
        meal = Meal.Dinner
    ),
    MealItem(
        name = UiText.StringResource(R.string.snacks),
        drawableRes = R.drawable.ic_snack,
        meal = Meal.Snack
    ),
)