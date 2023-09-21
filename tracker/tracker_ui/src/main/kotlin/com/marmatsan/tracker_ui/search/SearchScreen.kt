package com.marmatsan.tracker_ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.marmatsan.core_domain.util.UiEvent
import com.marmatsan.core_ui.LocalSpacing
import com.marmatsan.tracker_domain.model.Meal
import com.marmatsan.tracker_domain.model.TrackableFood
import com.marmatsan.tracker_ui.R
import com.marmatsan.tracker_ui.search.components.SearchTextField
import com.marmatsan.tracker_ui.search.components.TrackableFoodItem
import java.time.LocalDate

@ExperimentalComposeUiApi
@Composable
fun SearchScreen(
    mealName: String,
    dayOfMonth: Int,
    month: Int,
    year: Int,
    onNavigateUp: () -> Unit,
    snackbarHostState: SnackbarHostState,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                    keyboardController?.hide()
                }

                is UiEvent.NavigateUp -> onNavigateUp()
                else -> Unit
            }
        }
    }
    SearchScreenContent(
        mealName = mealName,
        state = viewModel.state,
        onValueChange = {
            viewModel.onEvent(SearchEvent.OnQueryChange(it))
        },
        onSearch = {
            keyboardController?.hide()
            viewModel.onEvent(SearchEvent.OnSearch)
        },
        onFocusChanged = {
            viewModel.onEvent(SearchEvent.OnSearchFocusChange(it.isFocused))
        },
        onToggleTrackableFood = {
            viewModel.onEvent(SearchEvent.OnToggleTrackableFood(it))
        },
        onAmountChange = { trackableFood, meal ->
            viewModel.onEvent(SearchEvent.OnAmountForFoodChange(trackableFood, meal))
        },
        onTrack = {
            keyboardController?.hide()
            viewModel.onEvent(
                SearchEvent.OnTrackFoodClick(
                    food = it,
                    meal = when (mealName) { // TODO: Change logic
                        "Breakfast" -> Meal.Breakfast
                        "Lunch" -> Meal.Lunch
                        "Dinner" -> Meal.Dinner
                        "Snack" -> Meal.Snack
                        else -> Meal.Breakfast
                    },
                    date = LocalDate.of(year, month, dayOfMonth)
                )
            )
        }
    )
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun SearchScreenContent(
    mealName: String,
    state: SearchState,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit,
    onFocusChanged: (FocusState) -> Unit,
    onToggleTrackableFood: (TrackableFood) -> Unit,
    onAmountChange: (TrackableFood, String) -> Unit,
    onTrack: (TrackableFood) -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(
                    top = spacing.spaceMedium,
                    bottom = spacing.spaceLarge
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.add_meal, mealName.lowercase()),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(spacing.spaceMedium))
            SearchTextField(
                modifier = modifier.padding(
                    start = spacing.spaceSmall,
                    end = spacing.spaceSmall
                ),
                text = state.query,
                hintVisible = state.isHintVisible,
                onValueChange = {
                    onValueChange(it)
                },
                onSearch = {
                    onSearch()
                },
                onFocusChanged = {
                    onFocusChanged(it)
                }
            )
        }
        Spacer(Modifier.height(spacing.spaceMedium))
        if (state.isSearching || state.trackableFoods.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when {
                    state.isSearching -> CircularProgressIndicator()
                    state.trackableFoods.isEmpty() -> {
                        Text(
                            text = stringResource(id = R.string.no_results),
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.trackableFoods) { trackableFoodUiState ->
                    TrackableFoodItem(
                        modifier = Modifier.fillMaxWidth(),
                        trackableFoodUiState = trackableFoodUiState,
                        onClick = {
                            onToggleTrackableFood(trackableFoodUiState.food)
                        },
                        onAmountChange = {
                            onAmountChange(trackableFoodUiState.food, it)
                        },
                        onTrack = {
                            onTrack(trackableFoodUiState.food)
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun SearchScreenContentPreview() {
    SearchScreenContent(
        mealName = "breakfast",
        state = SearchState(),
        onValueChange = { },
        onSearch = { },
        onFocusChanged = { },
        onToggleTrackableFood = {},
        onAmountChange = { _, _ -> },
        onTrack = {}
    )
}