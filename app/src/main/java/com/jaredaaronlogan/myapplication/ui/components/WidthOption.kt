    package com.jaredaaronlogan.myapplication.ui.components

import androidx.compose.foundation.layout.Spacer
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
fun WidthOption(
    color: Int,
    size: Float,
    screenWidth: Float,
    onClick: () -> Unit
) {
    Spacer(modifier = Modifier.width((screenWidth / 15).dp))
    Button(
        modifier = Modifier
            .width(size.dp)
            .height(size.dp)
            .padding(horizontal = 5.dp, vertical = 5.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(Color(color)),
        onClick = onClick
    ) {
    }
}