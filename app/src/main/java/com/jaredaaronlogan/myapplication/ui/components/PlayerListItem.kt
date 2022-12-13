package com.jaredaaronlogan.myapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jaredaaronlogan.myapplication.ui.models.Player

@Composable
fun PlayerListItem(
    player: Player,
) {
    Surface(
        elevation = 2.dp,
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column (
            modifier = Modifier.fillMaxSize()
                .background(color = if (player.ready == true) Color(0xFF419428) else Color(0xFFfCD5CE))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp, 0.dp)
                    .background(color = if (player.ready == true) Color(0xFF419428) else Color(0xFFfCD5CE))
                    .fillMaxSize()
            ) {
                Text(text = player.screenName ?: "Default Screen Name", style = MaterialTheme.typography.h6)
            }
        }
    }
}