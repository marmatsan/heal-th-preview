package com.marmatsan.tracker_data.remote.api

import com.marmatsan.tracker_data.remote.dto.SearchDataDto

interface OpenFoodApi {
    suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int,
    ): SearchDataDto
}