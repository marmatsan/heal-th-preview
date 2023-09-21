package com.marmatsan.core_domain.util

sealed class UiEvent {
    data class Navigate(val route: String) : UiEvent()
    object NavigateDown : UiEvent()
    object NavigateUp : UiEvent()
    data class ShowSnackBar(val message: UiText) : UiEvent()
}