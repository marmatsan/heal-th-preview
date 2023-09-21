package com.marmatsan.onboarding_ui.gender

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marmatsan.core_domain.model.Gender
import com.marmatsan.core_domain.navigation.Route
import com.marmatsan.core_domain.preferences.Preferences
import com.marmatsan.core_domain.util.UiEvent
import com.marmatsan.core_domain.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import onboarding_domain.use_case.ValidateGender
import javax.inject.Inject

@HiltViewModel
class GenderViewModel @Inject constructor(
    private val preferences: Preferences,
    private val validateGender: ValidateGender
) : ViewModel() {

    var selectedGender by mutableStateOf<Gender>(Gender.Unknown)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: GenderEvent) {
        when (event) {
            is GenderEvent.OnGenderEnter -> {
                selectedGender = event.gender
            }

            is GenderEvent.OnNextClicked -> {
                when (val result = validateGender(selectedGender)) {
                    is ValidateGender.Result.Success -> {
                        viewModelScope.launch {
                            preferences.saveGender(selectedGender)
                            _uiEvent.send(UiEvent.Navigate(Route.OnBoarding.AGE))
                        }
                    }

                    is ValidateGender.Result.Error -> {
                        viewModelScope.launch {
                            _uiEvent.send(UiEvent.ShowSnackBar(message = result.message))
                        }
                    }
                }
            }
        }
    }
}