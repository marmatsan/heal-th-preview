package com.marmatsan.onboarding_ui.nutrient_goal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marmatsan.core_domain.preferences.Preferences
import com.marmatsan.core_domain.use_case.FilterOutDigits
import com.marmatsan.core_domain.navigation.Route
import com.marmatsan.core_domain.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import onboarding_domain.use_case.ValidateNutrients
import javax.inject.Inject

@HiltViewModel
class NutrientGoalViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterOutDigits: FilterOutDigits,
    private val validateNutrients: ValidateNutrients
) : ViewModel() {

    var state by mutableStateOf(NutrientGoalState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: NutrientGoalEvent) {
        when (event) {
            is NutrientGoalEvent.OnCarbPctEnter -> {
                state = state.copy(
                    carbsPct = filterOutDigits(event.carbPct)
                )
            }

            is NutrientGoalEvent.OnProteinPctEnter -> {
                state = state.copy(
                    proteinPct = filterOutDigits(event.proteinPct)
                )
            }

            is NutrientGoalEvent.OnFatPctEnter -> {
                state = state.copy(
                    fatPct = filterOutDigits(event.fatPct)
                )
            }

            NutrientGoalEvent.OnBackClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.NavigateDown)
                }
            }

            is NutrientGoalEvent.OnNextClick -> {
                val result = validateNutrients(
                    carbsPctText = state.carbsPct,
                    proteinPctText = state.proteinPct,
                    fatPctText = state.fatPct
                )
                when (result) {
                    is ValidateNutrients.Result.Success -> {
                        viewModelScope.launch {
                            preferences.saveCarbRatio(result.carbsRatio)
                            preferences.saveProteinRatio(result.proteinRatio)
                            preferences.saveFatRatio(result.fatRatio)
                            _uiEvent.send(UiEvent.Navigate(Route.Tracker.OVERVIEW))
                        }
                    }

                    is ValidateNutrients.Result.Error -> {
                        viewModelScope.launch {
                            _uiEvent.send(UiEvent.ShowSnackBar(result.message))
                        }
                    }
                }
            }

        }
    }
}