package onboarding_domain.use_case

import com.marmatsan.core_domain.R
import com.marmatsan.core_domain.util.UiText

class ValidateNutrients {

    operator fun invoke(
        carbsPctText: String,
        proteinPctText: String,
        fatPctText: String
    ): Result {
        val carbsPct = carbsPctText.toIntOrNull()
        val proteinPct = proteinPctText.toIntOrNull()
        val fatPct = fatPctText.toIntOrNull()
        if (carbsPct == null || proteinPct == null || fatPct == null) {
            return Result.Error(
                message = UiText.StringResource(R.string.error_invalid_values)
            )
        }
        if (carbsPct + proteinPct + fatPct != 100) {
            return Result.Error(
                message = UiText.StringResource(R.string.error_not_100_percent)
            )
        }
        return Result.Success(
            carbsRatio = carbsPct / 100f,
            proteinRatio = proteinPct / 100f,
            fatRatio = fatPct / 100f
        )
    }

    sealed class Result {
        data class Success(
            val carbsRatio: Float,
            val proteinRatio: Float,
            val fatRatio: Float
        ) : Result()

        data class Error(val message: UiText) : Result()
    }
}