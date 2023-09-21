package com.marmatsan.tracker_data.mapper

import com.marmatsan.tracker_data.remote.dto.ProductDto
import com.marmatsan.tracker_domain.model.TrackableFood
import kotlin.math.roundToInt

fun ProductDto.toTrackableFood(): TrackableFood? {
    return TrackableFood( // TODO: Improve logic
        name = productName ?: return null,
        carbsPer100g = nutriments.carbohydrates100g?.roundToInt() ?: return null,
        proteinPer100g = nutriments.proteins100g?.roundToInt() ?: return null,
        fatPer100g = nutriments.fat100g?.roundToInt() ?: return null,
        caloriesPer100g = nutriments.energyKcal100g?.roundToInt() ?: return null,
        imageUrl = imageFrontThumbUrl ?: return null
    )
}