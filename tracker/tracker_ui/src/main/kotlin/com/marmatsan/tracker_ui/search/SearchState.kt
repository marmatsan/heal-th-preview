package com.marmatsan.tracker_ui.search

import com.marmatsan.core_domain.util.Empty

data class SearchState(
    val query: String = String.Empty,
    val isHintVisible: Boolean = true,
    val isSearching: Boolean = false,
    val trackableFoods: List<TrackableFoodUiState> = emptyList()
)