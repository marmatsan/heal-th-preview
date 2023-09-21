package com.marmatsan.onboarding_ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.marmatsan.core_ui.LocalSpacing

@Composable
fun SelectableButton(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    if (isSelected) {
        Button(
            onClick = {
                onClick()
            }
        ) {
            Text(text = text)
        }
    } else {
        OutlinedButton(
            onClick = {
                onClick()
            }
        ) {
            Text(text = text)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectableButtonPreview() {
    SelectableButton(
        text = "Selectable button",
        isSelected = true,
        onClick = { }
    )
}
