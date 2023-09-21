package onboarding_domain.use_case

import com.marmatsan.core_domain.model.Gender
import com.marmatsan.core_domain.util.UiText
import com.marmatsan.core_domain.R

class ValidateGender {

    operator fun invoke(
        gender: Gender
    ): Result {
        return if (gender is Gender.Unknown) {
            Result.Error(message = UiText.StringResource(R.string.error_select_gender))
        } else {
            Result.Success
        }
    }

    sealed class Result {
        object Success : Result()
        data class Error(val message: UiText) : Result()
    }
}