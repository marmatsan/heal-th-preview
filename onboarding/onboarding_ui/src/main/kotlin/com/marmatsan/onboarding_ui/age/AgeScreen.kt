package com.marmatsan.onboarding_ui.age

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
fun AgeScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    onNavigate: (UiEvent.Navigate) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: AgeViewModel = hiltViewModel()
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

                is UiEvent.NavigateDown -> {
                    onNavigateBack()
                }

                else -> Unit
            }
        }
    }

    AgeScreenContent(
        modifier = modifier,
        age = viewModel.age,
        onAgeEnter = {
            viewModel.onEvent(AgeEvent.OnAgeEnter(it))
        },
        onBackClicked = {
            viewModel.onEvent(AgeEvent.OnBackClicked)
        },
        onNextClicked = {
            viewModel.onEvent(AgeEvent.OnNextClicked)
        }
    )

}

@Composable
fun AgeScreenContent(
    modifier: Modifier = Modifier,
    age: String,
    onAgeEnter: (String) -> Unit,
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
                text = stringResource(id = R.string.whats_your_age)
            )
            Spacer(Modifier.height(spacing.spaceMedium))
            UnitTextField(
                value = age,
                onValueChange = {
                    onAgeEnter(it)
                },
                unit = stringResource(id = R.string.years)
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
fun AgeScreenContentPreview() {
    AgeScreenContent(
        age = "20",
        onAgeEnter = { },
        onBackClicked = { },
        onNextClicked = { }
    )
}