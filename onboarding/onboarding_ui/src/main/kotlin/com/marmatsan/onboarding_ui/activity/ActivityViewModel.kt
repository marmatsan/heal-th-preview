package com.marmatsan.onboarding_ui.activity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marmatsan.core_domain.model.ActivityLevel
import com.marmatsan.core_domain.navigation.Route
import com.marmatsan.core_domain.preferences.Preferences
import com.marmatsan.core_domain.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val preferences: Preferences
) : ViewModel() {

    var selectedActivityLevel by mutableStateOf<ActivityLevel>(ActivityLevel.Medium)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: ActivityEvent) {
        when (event) {
            is ActivityEvent.OnActivityLevelEnter -> {
                selectedActivityLevel = event.activityLevel
            }

            is ActivityEvent.OnBackClicked -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.NavigateDown)
                }
            }

            is ActivityEvent.OnNextClicked -> {
                viewModelScope.launch {
                    preferences.saveActivityLevel(selectedActivityLevel)
                    _uiEvent.send(UiEvent.Navigate(Route.OnBoarding.WEIGHT_GOAL))
                }
            }
        }
    }
}