package com.marmatsan.tracker_ui.search

import com.marmatsan.core_domain.util.Empty
import com.marmatsan.tracker_domain.model.TrackableFood

data class TrackableFoodUiState(
    val food: TrackableFood,
    val isExpanded: Boolean = false,
    val amount: String = String.Empty
)