package com.marmatsan.tracker_data.repository

import com.marmatsan.tracker_data.local.TrackerDao
import com.marmatsan.tracker_data.mapper.toTrackableFood
import com.marmatsan.tracker_data.mapper.toTrackedFood
import com.marmatsan.tracker_data.mapper.toTrackedFoodEntity
import com.marmatsan.tracker_data.remote.api.OpenFoodApi
import com.marmatsan.tracker_domain.model.TrackableFood
import com.marmatsan.tracker_domain.model.TrackedFood
import com.marmatsan.tracker_domain.repository.RequestState
import com.marmatsan.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.temporal.TemporalQuery

class TrackerRepositoryImpl(
    private val dao: TrackerDao,
    private val api: OpenFoodApi
) : TrackerRepository {

    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int
    ): Flow<RequestState<List<TrackableFood>>> {
        return flow {
            emit(RequestState.Loading(isLoading = true))

            val remoteProducts = try {
                val response = api.searchFood(
                    query = query,
                    page = page,
                    pageSize = pageSize
                )
                response.products.mapNotNull { it.toTrackableFood() }
            } catch (e: Exception) { // TODO: Ktor error handling
                emit(RequestState.Error(e.message.orEmpty()))
                null
            }

            emit(RequestState.Success(remoteProducts))

        }
    }

    override suspend fun insertTrackedFood(food: TrackedFood) {
        dao.insertTrackedFood(food.toTrackedFoodEntity())
    }

    override suspend fun deleteTrackedFood(food: TrackedFood) {
        dao.deleteTrackedFood(food.toTrackedFoodEntity())
    }

    override fun getFoodsForDate(localDate: LocalDate): Flow<List<TrackedFood>> {
        return dao.getFoodsForDate(
            day = localDate.dayOfMonth,
            month = localDate.monthValue,
            year = localDate.year
        ).map { entities ->
            entities.map { it.toTrackedFood() }
        }
    }
}