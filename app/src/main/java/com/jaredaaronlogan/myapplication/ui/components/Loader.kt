package com.usu.firebasetodosapplication.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Loader() {
    val transition = rememberInfiniteTransition()
    val sizePercentage by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            repeatMode = RepeatMode.Reverse,
            animation = tween(durationMillis = 1000),
        )
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            modifier = Modifier.size(
                ((50 * sizePercentage) + 50).dp,
            ),
            shape = RoundedCornerShape(100),
            elevation = 2.dp
        ) {

        }
        Text(text = "Waiting for other players...", modifier = Modifier.padding(top = 4.dp))
    }
}