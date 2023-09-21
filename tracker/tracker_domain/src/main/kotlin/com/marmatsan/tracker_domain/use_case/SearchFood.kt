package com.marmatsan.tracker_domain.use_case

import com.marmatsan.tracker_domain.model.TrackableFood
import com.marmatsan.tracker_domain.repository.RequestState
import com.marmatsan.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single

class SearchFood(
    private val repository: TrackerRepository
) {
    suspend operator fun invoke(
        query: String,
        page: Int = 1,
        pageSize: Int = 40
    ): Flow<RequestState<List<TrackableFood>>> {
        return repository
            .searchFood(
                query = query,
                page = page,
                pageSize = pageSize
            )

    }
}