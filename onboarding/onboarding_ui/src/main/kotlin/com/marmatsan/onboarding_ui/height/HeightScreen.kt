package com.marmatsan.onboarding_ui.height

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
fun HeightScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    onNavigate: (UiEvent.Navigate) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: HeightViewModel = hiltViewModel()
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

                is UiEvent.NavigateDown -> onNavigateBack()

                else -> Unit
            }
        }
    }

    HeightScreenContent(
        modifier = modifier,
        height = viewModel.height,
        onHeightEnter = {
            viewModel.onEvent(HeightEvent.OnHeightEnter(it))
        },
        onBackClicked = {
            viewModel.onEvent(HeightEvent.OnBackClicked)
        },
        onNextClicked = {
            viewModel.onEvent(HeightEvent.OnNextClicked)
        }
    )
}

@Composable
fun HeightScreenContent(
    modifier: Modifier = Modifier,
    height: String,
    onHeightEnter: (String) -> Unit,
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
                text = stringResource(id = R.string.whats_your_height)
            )
            Spacer(modifier.height(spacing.spaceMedium))
            UnitTextField(
                value = height,
                onValueChange = {
                    onHeightEnter(it)
                },
                unit = stringResource(id = R.string.cm)
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

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun HeightScreenContentPreview() {
    HeightScreenContent(
        height = "140",
        onHeightEnter = { },
        onBackClicked = { },
        onNextClicked = { }
    )
}