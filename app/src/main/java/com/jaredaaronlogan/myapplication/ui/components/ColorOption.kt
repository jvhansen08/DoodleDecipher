package com.jaredaaronlogan.myapplication.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ColorOption(
    color: Color,
    onClick:() -> Unit,
) {
    Button(
        modifier = Modifier
            .width(40.dp)
            .height(40.dp)
            .padding(5.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(color),
        onClick = onClick,
    ) {
    }
}