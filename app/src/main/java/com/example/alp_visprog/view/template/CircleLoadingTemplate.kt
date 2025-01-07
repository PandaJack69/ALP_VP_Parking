package com.example.alp_visprog.view.template

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CircleLoadingTemplate(
    modifier: Modifier = Modifier,
    color: Color,
    trackColor: Color
) {
    CircularProgressIndicator(
        modifier = modifier,
        color = color,
        trackColor = trackColor,
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun CircleLoadingPreview() {
    CircleLoadingTemplate(
        modifier = Modifier
            .size(40.dp),
        color = Color.Black,
        trackColor = Color.Transparent
    )
}