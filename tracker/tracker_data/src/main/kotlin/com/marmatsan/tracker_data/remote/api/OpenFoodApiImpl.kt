package com.marmatsan.tracker_data.remote.api

import com.marmatsan.tracker_data.BuildConfig
import com.marmatsan.tracker_data.remote.dto.SearchDataDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject

class OpenFoodApiImpl @Inject constructor(
    private val httpClient: HttpClient
) : OpenFoodApi {

    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int,
    ): SearchDataDto =
        httpClient.get {
            url(
                BuildConfig.BASE_URL.plus("/cgi/search.pl?json=true&action=process&fields=product_name,nutriments,image_front_thumb_url&page=1&page_size=20")
            )
            parameter("search_terms", query)
        }.body()

}