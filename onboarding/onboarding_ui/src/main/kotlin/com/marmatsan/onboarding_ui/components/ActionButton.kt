package com.marmatsan.onboarding_ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.marmatsan.core_ui.LocalSpacing

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    isEnabled: Boolean = true
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = isEnabled
    ) {
        Text(
            modifier = modifier.padding(LocalSpacing.current.spaceSmall),
            text = text
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ActionButtonPreview() {
    ActionButton(
        text = "Next",
        onClick = { }
    )
}