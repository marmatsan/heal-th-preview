package com.marmatsan.tracker_ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.marmatsan.core_ui.LocalSpacing

@Composable
fun UnitDisplay(
    modifier: Modifier = Modifier,
    amountColor: Color = MaterialTheme.colorScheme.onPrimary,
    unitColor: Color = MaterialTheme.colorScheme.onPrimary,
    amount: Int,
    unit: String,
    amountTextSize: TextUnit = 20.sp,
    unitTextSize: TextUnit = 14.sp,
) {
    val spacing = LocalSpacing.current
    Row(modifier = modifier) {
        Text(
            text = amount.toString(),
            color = amountColor,
            fontSize = amountTextSize,
            modifier = Modifier.alignBy(LastBaseline)
        )
        Spacer(Modifier.width(spacing.spaceExtraSmall))
        Text(
            text = unit,
            color = unitColor,
            fontSize = unitTextSize,
            modifier = Modifier.alignBy(LastBaseline)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UnitDisplayPreview() {
    UnitDisplay(
        amount = 5,
        unit = "g"
    )
}