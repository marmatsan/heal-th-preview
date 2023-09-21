package com.marmatsan.onboarding_ui.age

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

import com.marmatsan.core_domain.preferences.Preferences
import com.marmatsan.core_domain.use_case.FilterOutDigits
import com.marmatsan.core_domain.util.UiEvent
import com.marmatsan.core_domain.util.UiText
import com.marmatsan.core_domain.R
import com.marmatsan.core_domain.navigation.Route

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AgeViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterOutDigits: FilterOutDigits
) : ViewModel() {

    var age by mutableStateOf<String>("20")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: AgeEvent) {
        when (event) {
            is AgeEvent.OnAgeEnter -> {
                if (age.length <= 3) {
                    this.age = filterOutDigits(event.age)
                }
            }

            is AgeEvent.OnBackClicked -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.NavigateDown)
                }
            }

            is AgeEvent.OnNextClicked -> {
                viewModelScope.launch {
                    val ageNumber = age.toIntOrNull() ?: run {
                        _uiEvent.send(
                            UiEvent.ShowSnackBar(
                                UiText.StringResource(R.string.error_age_cant_be_empty)
                            )
                        )
                        return@launch
                    }
                    preferences.saveAge(ageNumber)
                    _uiEvent.send(UiEvent.Navigate(Route.OnBoarding.HEIGHT))
                }
            }
        }
    }
}