package com.marmatsan.core_domain.model

import kotlinx.serialization.Serializable

@Serializable
sealed class WeightGoal {

    enum class WeightGoalType {
        LOSE_WEIGHT, KEEP_WEIGHT, GAIN_WEIGHT
    }

    @Serializable
    object LoseWeight : WeightGoal()

    @Serializable
    object KeepWeight : WeightGoal()

    @Serializable
    object GainWeight : WeightGoal()

    companion object {
        fun fromType(type: WeightGoalType): WeightGoal {
            return when (type) {
                WeightGoalType.LOSE_WEIGHT -> LoseWeight
                WeightGoalType.KEEP_WEIGHT -> KeepWeight
                WeightGoalType.GAIN_WEIGHT -> GainWeight
            }
        }
    }
}