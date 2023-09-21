package com.marmatsan.onboarding_ui.weight_goal

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.marmatsan.core_domain.R
import com.marmatsan.core_domain.model.WeightGoal
import com.marmatsan.core_domain.util.UiEvent
import com.marmatsan.core_ui.LocalSpacing
import com.marmatsan.onboarding_ui.components.ActionButton
import com.marmatsan.onboarding_ui.components.SelectableButton

@Composable
fun GoalScreen(
    modifier: Modifier = Modifier,
    onNavigate: (UiEvent.Navigate) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: GoalViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.NavigateDown -> onNavigateBack()
                else -> Unit
            }
        }
    }

    GoalScreenContent(
        modifier = modifier,
        selectedWeightGoal = viewModel.selectedWeightGoal,
        onGoalEnter = {
            viewModel.onEvent(WeightGoalEvent.OnWeightGoalEnter(it))
        },
        onNextClick = {
            viewModel.onEvent(WeightGoalEvent.OnNextClicked)
        },
        onBackClick = {
            viewModel.onEvent(WeightGoalEvent.OnBackClicked)
        }
    )
}

@Composable
fun GoalScreenContent(
    modifier: Modifier = Modifier,
    selectedWeightGoal: WeightGoal,
    onGoalEnter: (WeightGoal) -> Unit,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val spacing = LocalSpacing.current

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .weight(9f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.lose_keep_or_gain_weight)
                )
                Spacer(modifier.height(spacing.spaceMedium))
                Row {
                    SelectableButton(
                        text = stringResource(id = R.string.lose),
                        isSelected = selectedWeightGoal is WeightGoal.LoseWeight,
                        onClick = {
                            onGoalEnter(WeightGoal.LoseWeight)
                        }
                    )
                    Spacer(Modifier.width(spacing.spaceMedium))
                    SelectableButton(
                        text = stringResource(id = R.string.keep),
                        isSelected = selectedWeightGoal is WeightGoal.KeepWeight,
                        onClick = {
                            onGoalEnter(WeightGoal.KeepWeight)
                        }
                    )
                    Spacer(Modifier.width(spacing.spaceMedium))
                    SelectableButton(
                        text = stringResource(id = R.string.gain),
                        isSelected = selectedWeightGoal is WeightGoal.GainWeight,
                        onClick = {
                            onGoalEnter(WeightGoal.GainWeight)
                        }
                    )
                }
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
                        onBackClick()
                    }
                )
                ActionButton(
                    text = stringResource(id = R.string.next),
                    onClick = {
                        onNextClick()
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun GoalScreenContentPreview() {
    GoalScreenContent(
        selectedWeightGoal = WeightGoal.KeepWeight,
        onGoalEnter = { },
        onNextClick = { },
        onBackClick = { }
    )
}