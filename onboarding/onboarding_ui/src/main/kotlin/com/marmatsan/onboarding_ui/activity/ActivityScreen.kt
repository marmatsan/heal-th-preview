package com.marmatsan.onboarding_ui.activity

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
import com.marmatsan.core_domain.model.ActivityLevel
import com.marmatsan.core_domain.util.UiEvent
import com.marmatsan.core_ui.LocalSpacing
import com.marmatsan.onboarding_ui.components.ActionButton
import com.marmatsan.onboarding_ui.components.SelectableButton

@Composable
fun ActivityScreen(
    modifier: Modifier = Modifier,
    onNavigate: (UiEvent.Navigate) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: ActivityViewModel = hiltViewModel()
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

    ActivityScreenContent(
        modifier = modifier,
        selectedActivityLevel = viewModel.selectedActivityLevel,
        onActivityLeveEnter = {
            viewModel.onEvent(ActivityEvent.OnActivityLevelEnter(it))
        },
        onNextClick = {
            viewModel.onEvent(ActivityEvent.OnNextClicked)
        },
        onBackClick = {
            viewModel.onEvent(ActivityEvent.OnBackClicked)
        }
    )
}

@Composable
fun ActivityScreenContent(
    modifier: Modifier = Modifier,
    selectedActivityLevel: ActivityLevel,
    onActivityLeveEnter: (ActivityLevel) -> Unit,
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
                    text = stringResource(id = R.string.whats_your_activity_level)
                )
                Spacer(Modifier.height(spacing.spaceMedium))
                Row {
                    SelectableButton(
                        text = stringResource(id = R.string.low),
                        isSelected = selectedActivityLevel is ActivityLevel.Low,
                        onClick = {
                            onActivityLeveEnter(ActivityLevel.Low)
                        }
                    )
                    Spacer(Modifier.width(spacing.spaceMedium)
                    )
                    SelectableButton(
                        text = stringResource(id = R.string.medium),
                        isSelected = selectedActivityLevel is ActivityLevel.Medium,
                        onClick = {
                            onActivityLeveEnter(ActivityLevel.Medium)
                        }
                    )
                    Spacer(Modifier.width(spacing.spaceMedium))
                    SelectableButton(
                        text = stringResource(id = R.string.high),
                        isSelected = selectedActivityLevel is ActivityLevel.High,
                        onClick = {
                            onActivityLeveEnter(ActivityLevel.High)
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
fun ActivityScreenContentPreview() {
    ActivityScreenContent(
        selectedActivityLevel = ActivityLevel.Medium,
        onActivityLeveEnter = { },
        onNextClick = { },
        onBackClick = { }
    )
}