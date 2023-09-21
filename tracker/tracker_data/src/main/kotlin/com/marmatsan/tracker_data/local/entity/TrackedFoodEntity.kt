package com.marmatsan.tracker_data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.marmatsan.tracker_domain.model.Meal.MealType

@Entity
data class TrackedFoodEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val carbs: Int,
    val protein: Int,
    val fat: Int,
    val imageUrl: String?,
    val mealType: MealType,
    val amount: Int,
    val dayOfMonth: Int,
    val month: Int,
    val year: Int,
    val calories: Int
)