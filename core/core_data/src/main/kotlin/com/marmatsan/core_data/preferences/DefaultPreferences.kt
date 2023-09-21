package com.marmatsan.core_data.preferences

import androidx.datastore.core.DataStore
import com.marmatsan.core_domain.model.ActivityLevel
import com.marmatsan.core_domain.model.Gender
import com.marmatsan.core_domain.model.WeightGoal
import com.marmatsan.core_domain.preferences.Preferences
import com.marmatsan.core_domain.preferences.PreferencesData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultPreferences @Inject constructor(
    private val dataStore: DataStore<PreferencesData>
) : Preferences {

    override suspend fun saveGender(gender: Gender) {
        dataStore.updateData {
            it.copy(
                userInfo = it.userInfo.copy(
                    gender = gender
                )
            )
        }
    }

    override suspend fun saveAge(age: Int) {
        dataStore.updateData {
            it.copy(
                userInfo = it.userInfo.copy(
                    age = age
                )
            )
        }
    }

    override suspend fun saveWeight(weight: Float) {
        dataStore.updateData {
            it.copy(
                userInfo = it.userInfo.copy(
                    weight = weight
                )
            )
        }
    }

    override suspend fun saveHeight(height: Int) {
        dataStore.updateData {
            it.copy(
                userInfo = it.userInfo.copy(
                    height = height
                )
            )
        }
    }

    override suspend fun saveActivityLevel(activityLevel: ActivityLevel) {
        dataStore.updateData {
            it.copy(
                userInfo = it.userInfo.copy(
                    activityLevel = activityLevel
                )
            )
        }
    }

    override suspend fun saveGoalType(weightGoal: WeightGoal) {
        dataStore.updateData {
            it.copy(
                userInfo = it.userInfo.copy(
                    weightGoal = weightGoal
                )
            )
        }
    }

    override suspend fun saveCarbRatio(carbRatio: Float) {
        dataStore.updateData {
            it.copy(
                userInfo = it.userInfo.copy(
                    carbRatio = carbRatio
                )
            )
        }
    }

    override suspend fun saveProteinRatio(proteinRatio: Float) {
        dataStore.updateData {
            it.copy(
                userInfo = it.userInfo.copy(
                    proteinRatio = proteinRatio
                )
            )
        }
    }

    override suspend fun saveFatRatio(fatRatio: Float) {
        dataStore.updateData {
            it.copy(
                userInfo = it.userInfo.copy(
                    fatRatio = fatRatio
                )
            )
        }
    }

    override suspend fun saveShowOnboarding(showOnboarding: Boolean) {
        dataStore.updateData {
            it.copy(
                showOnboarding = showOnboarding
            )
        }
    }

    override fun loadPreferencesData(): Flow<PreferencesData> {
        return dataStore.data
    }
}