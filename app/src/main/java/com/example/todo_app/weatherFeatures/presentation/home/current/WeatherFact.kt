package com.example.todo_app.weatherFeatures.presentation.home.current

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Face
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun WeatherFact(item: WeatherFact) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.label,
            modifier = Modifier.size(28.dp)
        )
        Spacer(Modifier.width(16.dp))
        Column {
            Text(
                text = item.label,
                style = MaterialTheme.typography.h3,
            )
            Spacer(Modifier.height(4.dp))
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = item.value,
                    style = MaterialTheme.typography.body1,
                )
            }
        }
    }
}

@Preview
@Composable
fun WeatherFactPreview() {
    val item = WeatherFact(
        label = "Temperature",
        value = "$21°  |  $6°",
        icon = Icons.Outlined.Face
    )
    WeatherFact(item)
}
