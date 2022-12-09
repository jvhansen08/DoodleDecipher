package com.jaredaaronlogan.myapplication.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ColorOption(
    color: Int,
    width: Float,
    onClick:() -> Unit,
) {
    Button(
        modifier = Modifier
            .fillMaxHeight(.9f)
            .fillMaxWidth(width)
            .padding(5.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(Color(color)),
        onClick = onClick,
    ) {
    }
}