package com.marmatsan.onboarding_ui.weight

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marmatsan.core_domain.preferences.Preferences
import com.marmatsan.core_domain.util.UiEvent
import com.marmatsan.core_domain.util.UiText
import com.marmatsan.core_domain.R
import com.marmatsan.core_domain.navigation.Route
import com.marmatsan.core_domain.use_case.FilterOutWeight
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeightViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterOutWeight: FilterOutWeight
) : ViewModel() {

    var weight by mutableStateOf("80.0")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: WeightEvent) {
        when (event) {
            is WeightEvent.OnWeightEnter -> {
                if (weight.length <= 5) {
                    this.weight = filterOutWeight(event.weight)
                }
            }

            is WeightEvent.OnBackClicked -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.NavigateDown)
                }
            }

            is WeightEvent.OnNextClicked -> {
                viewModelScope.launch {
                    val weightNumber = weight.toFloatOrNull() ?: run {
                        _uiEvent.send(
                            UiEvent.ShowSnackBar(
                                UiText.StringResource(R.string.error_weight_cant_be_empty)
                            )
                        )
                        return@launch
                    }
                    preferences.saveWeight(weightNumber)
                    _uiEvent.send(UiEvent.Navigate(Route.OnBoarding.ACTIVITY))
                }
            }
        }
    }
}