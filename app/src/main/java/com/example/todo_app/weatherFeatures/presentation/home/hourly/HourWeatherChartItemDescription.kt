package com.example.todo_app.weatherFeatures.presentation.home.hourly

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo_app.weatherFeatures.Domain.models.HourWeather
import com.example.todo_app.weatherFeatures.Domain.models.WeatherFacts
import com.example.todo_app.weatherFeatures.presentation.util.getIconRes
import com.example.todo_app.weatherFeatures.presentation.util.getWeatheStateDes
import java.util.*

@Composable
fun HourWeatherChartItemDescription(
    item: HourWeather,
    selectedTime: Date,
    modifier: Modifier = Modifier,
    onWeatherTimeSelected: (Date) -> Unit
) {

    val calendar = Calendar.getInstance()
    calendar.time = selectedTime
    val selectedHour = calendar[Calendar.HOUR_OF_DAY]
    calendar.time = item.time
    val itemHour = calendar[Calendar.HOUR_OF_DAY]
    val isSelected = selectedHour == itemHour

    Card(
        shape = MaterialTheme.shapes.medium,
        backgroundColor = if (isSelected) MaterialTheme.colors.primary.copy(alpha = 0.2f) else Color.Transparent,
        elevation = 0.dp,
        border = if (selectedHour == itemHour) {
            BorderStroke(2.dp, MaterialTheme.colors.primary)
        } else {
            null
        }
    ) {
        Column(
            modifier = modifier
                .clickable { onWeatherTimeSelected(item.time) }
                .padding(vertical = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                painter = painterResource(item.facts.state.getIconRes(item.night)),
                contentDescription = stringResource(id = item.facts.state.getWeatheStateDes()),
                modifier = Modifier.size(36.dp),
            )

            calendar.time = item.time
            val currentHour = calendar[Calendar.HOUR_OF_DAY]
            Text(
                text = "%02d".format(currentHour),
                style = MaterialTheme.typography.h2
            )
        }
    }
}

@Preview
@Composable
fun HourWeatherChartItemDescriptionPreview() {
    val item = HourWeather(
        time = Date(),
        facts = WeatherFacts.Default,
        night = false
    )
    HourWeatherChartItemDescription(item, Date(), onWeatherTimeSelected = {})
}
