package com.marmatsan.core_domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    val gender: Gender? = null,
    val age: Int? = null,
    val weight: Float? = null,
    val height: Int? = null,
    val activityLevel: ActivityLevel? = null,
    val weightGoal: WeightGoal? = null,
    val carbRatio: Float? = null,
    val proteinRatio: Float? = null,
    val fatRatio: Float? = null
)