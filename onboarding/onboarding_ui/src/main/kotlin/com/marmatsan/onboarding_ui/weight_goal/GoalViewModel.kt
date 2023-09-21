package com.marmatsan.onboarding_ui.weight_goal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marmatsan.core_domain.model.WeightGoal
import com.marmatsan.core_domain.navigation.Route
import com.marmatsan.core_domain.preferences.Preferences
import com.marmatsan.core_domain.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalViewModel @Inject constructor(
    private val preferences: Preferences
) : ViewModel() {

    var selectedWeightGoal by mutableStateOf<WeightGoal>(WeightGoal.KeepWeight)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: WeightGoalEvent) {
        when (event) {
            is WeightGoalEvent.OnWeightGoalEnter -> {
                selectedWeightGoal = event.weightGoal

            }

            is WeightGoalEvent.OnBackClicked -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.NavigateDown)
                }
            }

            is WeightGoalEvent.OnNextClicked -> {
                viewModelScope.launch {
                    preferences.saveGoalType(selectedWeightGoal)
                    _uiEvent.send(UiEvent.Navigate(Route.OnBoarding.NUTRIENT_GOAL))
                }
            }
        }
    }
}