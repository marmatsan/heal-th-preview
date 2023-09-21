package com.marmatsan.onboarding_ui.gender

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
import com.marmatsan.core_domain.model.Gender
import com.marmatsan.core_domain.util.UiEvent
import com.marmatsan.core_ui.LocalSpacing
import com.marmatsan.onboarding_ui.components.ActionButton
import com.marmatsan.onboarding_ui.components.SelectableButton

@Composable
fun GenderScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: GenderViewModel = hiltViewModel()
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

    GenderScreenContent(
        modifier = modifier,
        selectedGender = viewModel.selectedGender,
        onGenderEnter = {
            viewModel.onEvent(GenderEvent.OnGenderEnter(it))
        },
        onNextClick = {
            viewModel.onEvent(GenderEvent.OnNextClicked)
        }
    )

}

@Composable
fun GenderScreenContent(
    modifier: Modifier = Modifier,
    selectedGender: Gender,
    onGenderEnter: (Gender) -> Unit,
    onNextClick: () -> Unit
) {
    val spacing = LocalSpacing.current

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
                text = stringResource(id = R.string.whats_your_gender)
            )
            Spacer(modifier.height(spacing.spaceMedium))
            Row {
                SelectableButton(
                    text = stringResource(id = R.string.male),
                    isSelected = selectedGender is Gender.Male,
                    onClick = {
                        onGenderEnter(Gender.Male)
                    }
                )
                Spacer(Modifier.width(spacing.spaceMedium))
                SelectableButton(
                    text = stringResource(id = R.string.female),
                    isSelected = selectedGender is Gender.Female,
                    onClick = {
                        onGenderEnter(Gender.Female)
                    }
                )
            }
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(spacing.spaceMedium)
                .weight(1f),
            horizontalArrangement = Arrangement.End
        ) {
            ActionButton(
                text = stringResource(id = R.string.next),
                onClick = {
                    onNextClick()
                }
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun GenderScreenContentPreview() {
    GenderScreenContent(
        selectedGender = Gender.Female,
        onGenderEnter = { },
        onNextClick = { }
    )
}