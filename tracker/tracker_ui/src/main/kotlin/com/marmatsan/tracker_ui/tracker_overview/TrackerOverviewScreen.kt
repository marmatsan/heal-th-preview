package com.marmatsan.tracker_ui.tracker_overview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.marmatsan.core_domain.util.UiEvent
import com.marmatsan.core_ui.LocalSpacing
import com.marmatsan.tracker_domain.model.TrackedFood
import com.marmatsan.tracker_ui.R
import com.marmatsan.tracker_ui.tracker_overview.components.*

@Composable
fun TrackerOverviewScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TrackerOverviewViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    TrackerOverviewScreenContent(
        modifier = modifier,
        state = viewModel.state,
        onPreviousDayClick = {
            viewModel.onEvent(TrackerOverviewEvent.OnPreviousDayClick)
        },
        onNextDayClick = {
            viewModel.onEvent(TrackerOverviewEvent.OnNextDayClick)
        },
        onToggleClick = {
            viewModel.onEvent(TrackerOverviewEvent.OnToggleMealClick(it))
        },
        onDeleteClick = {
            viewModel.onEvent(TrackerOverviewEvent.OnDeleteTrackedFoodClick(it))
        },
        onAddFood = { mealName ->
            viewModel.onEvent(TrackerOverviewEvent.OnAddFoodClick(mealName))
        }
    )
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun TrackerOverviewScreenContent(
    state: TrackerOverviewState,
    onPreviousDayClick: () -> Unit,
    onNextDayClick: () -> Unit,
    onToggleClick: (MealItem) -> Unit,
    onDeleteClick: (TrackedFood) -> Unit,
    onAddFood: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        item {
            NutrientsHeader(
                state = state
            )
            Spacer(Modifier.height(spacing.spaceMedium))
            DaySelector(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = spacing.spaceMedium
                    ),
                date = state.date,
                onPreviousDayClick = onPreviousDayClick,
                onNextDayClick = onNextDayClick
            )
            Spacer(Modifier.height(spacing.spaceMedium))
        }
        items(state.meals) { mealItem ->
            ExpandableMeal(
                modifier = Modifier.fillMaxWidth(),
                mealItem = mealItem,
                onToggleClick = {
                    onToggleClick(mealItem)
                }
            ) {
                // Tracked food list for the selected meal type (breakfast, lunch, etc)
                Column(
                    modifier = Modifier.padding(horizontal = spacing.spaceSmall)
                ) {
                    state.trackedFoods.forEach { trackedFood ->
                        TrackedFoodItem(
                            trackedFood = trackedFood,
                            onDeleteClick = {
                                onDeleteClick(trackedFood)
                            }
                        )
                        Spacer(Modifier.height(spacing.spaceMedium))
                    }
                    AddButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(
                            id = R.string.add_meal,
                            mealItem.name.asString(context).lowercase()
                        ),
                        onClick = {
                            onAddFood(mealItem.name.asString(context))
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TrackerOverviewScreenContentPreview() {
    TrackerOverviewScreenContent(
        modifier = Modifier,
        state = TrackerOverviewState(),
        onPreviousDayClick = { },
        onNextDayClick = { },
        onToggleClick = { },
        onDeleteClick = { },
        onAddFood = { }
    )
}