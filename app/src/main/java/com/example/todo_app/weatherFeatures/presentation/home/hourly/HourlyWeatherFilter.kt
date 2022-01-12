package com.example.todo_app.weatherFeatures.presentation.home.hourly

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo_app.R
import com.example.todo_app.weatherFeatures.Domain.models.HourlyWeatherType

@Composable
fun HourlyWeatherFilter(
    item: HourlyWeatherType,
    selected: Boolean,
    onFilterSelected: (HourlyWeatherType) -> Unit
) {
    Box(modifier = Modifier.padding(8.dp)) {
        Card(
            shape = MaterialTheme.shapes.small,
            backgroundColor = if (selected) {
                MaterialTheme.colors.primary.copy(alpha = 0.2f)
            } else {
                Color.Transparent
            },
            elevation = 0.dp,
            border = if (selected) {
                BorderStroke(2.dp, MaterialTheme.colors.primary)
            } else {
                null
            },
        ) {
            val suffix = when (item) {
                HourlyWeatherType.Temperature -> "  -  °C"
                HourlyWeatherType.Wind -> "  -  km/h"
                HourlyWeatherType.CloudCover -> "  -  %"
            }

            val message = when (item) {
                HourlyWeatherType.Temperature -> stringResource(id = R.string.temperature)
                HourlyWeatherType.Wind -> stringResource(id = R.string.wind)
                HourlyWeatherType.CloudCover -> stringResource(id = R.string.cloud_cover)
            }.run { if (selected) this + suffix else this }

            Text(
                modifier = Modifier
                    .clickable { onFilterSelected(item) }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                text = message,
                style = MaterialTheme.typography.h3.copy(
                    fontWeight = if (selected) {
                        FontWeight.Bold
                    } else {
                        FontWeight.Normal
                    }
                ),
            )
        }
    }
}

@Preview
@Composable
fun HourlyWeatherFilterPreview() {
    HourlyWeatherFilter(
        item = HourlyWeatherType.Temperature,
        selected = true,
        onFilterSelected = {}
    )
}
