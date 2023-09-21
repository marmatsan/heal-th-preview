package onboarding_domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import onboarding_domain.use_case.ValidateGender
import onboarding_domain.use_case.ValidateNutrients

@Module
@InstallIn(ViewModelComponent::class)
object OnboardingDomainModule {

    @Provides
    @ViewModelScoped
    fun provideValidateNutrientsUseCase(): ValidateNutrients {
        return ValidateNutrients()
    }

    @Provides
    @ViewModelScoped
    fun provideValidateGenderUseCase(): ValidateGender {
        return ValidateGender()
    }
}