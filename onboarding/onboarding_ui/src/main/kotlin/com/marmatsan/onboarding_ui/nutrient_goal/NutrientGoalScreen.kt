package com.marmatsan.onboarding_ui.nutrient_goal

import androidx.compose.foundation.layout.*
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.marmatsan.core_domain.R
import com.marmatsan.core_domain.util.UiEvent
import com.marmatsan.core_ui.LocalSpacing
import com.marmatsan.onboarding_ui.components.ActionButton
import com.marmatsan.onboarding_ui.components.UnitTextField

@Composable
fun NutrientGoalScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: NutrientGoalViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }

                else -> Unit
            }
        }
    }

    NutrientGoalContent(
        modifier = modifier,
        state = viewModel.state,
        onCarbPctEnter = {
            viewModel.onEvent(NutrientGoalEvent.OnCarbPctEnter(it))
        },
        onProteinPctEnter = {
            viewModel.onEvent(NutrientGoalEvent.OnProteinPctEnter(it))
        },
        onFatPctEnter = {
            viewModel.onEvent(NutrientGoalEvent.OnFatPctEnter(it))
        },
        onBackClicked = {
            viewModel.onEvent(NutrientGoalEvent.OnBackClick)
        },
        onNextClicked = {
            viewModel.onEvent(NutrientGoalEvent.OnNextClick)
        }
    )
}

@Composable
fun NutrientGoalContent(
    modifier: Modifier = Modifier,
    state: NutrientGoalState,
    onCarbPctEnter: (String) -> Unit,
    onProteinPctEnter: (String) -> Unit,
    onFatPctEnter: (String) -> Unit,
    onBackClicked: () -> Unit,
    onNextClicked: () -> Unit
) {
    val spacing = LocalSpacing.current

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .weight(9f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.what_are_your_nutrient_goals)
            )
            Spacer(modifier.height(spacing.spaceMedium))
            UnitTextField(
                value = state.carbsPct,
                onValueChange = {
                    onCarbPctEnter(it)
                },
                unit = stringResource(id = R.string.percent_carbs)
            )
            Spacer(modifier.height(spacing.spaceMedium))
            UnitTextField(
                value = state.proteinPct,
                onValueChange = {
                    onProteinPctEnter(it)
                },
                unit = stringResource(id = R.string.percent_proteins)
            )
            Spacer(modifier.height(spacing.spaceMedium))
            UnitTextField(
                value = state.fatPct,
                onValueChange = {
                    onFatPctEnter(it)
                },
                unit = stringResource(id = R.string.percent_fats)
            )
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(spacing.spaceMedium)
                .weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ActionButton(
                text = stringResource(id = R.string.back),
                onClick = {
                    onBackClicked()
                }
            )
            ActionButton(
                text = stringResource(id = R.string.next),
                onClick = {
                    onNextClicked()
                }
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun NutrientGoalContentPreview() {
    NutrientGoalContent(
        state = NutrientGoalState(),
        onCarbPctEnter = { },
        onProteinPctEnter = { },
        onFatPctEnter = { },
        onBackClicked = { },
        onNextClicked = { }
    )
}