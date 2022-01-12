package com.example.todo_app.weatherFeatures.presentation.home.hourly

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.todo_app.weatherFeatures.Domain.models.*
import com.example.todo_app.weatherFeatures.presentation.home.hourly.HourlyWeather
import java.util.*

@Composable
fun HourlyWeatherChart(
    hourlyWeather: HourlyWeather,
    selectedTime: Date,
    onWeatherTimeSelected: (Date) -> Unit,
    expanded: Boolean,
    type: HourlyWeatherType
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
    ) {
        Column(modifier = Modifier.animateContentSize()) {
            if (expanded) {
                HourlyWeatherCurve(hourlyWeather, type)
            }
            HourlyWeatherChartHorizontalAxisDescription(
                hourlyWeather,
                selectedTime,
                onWeatherTimeSelected
            )
        }
    }
}

@Preview
@Composable
fun HourlyWeatherChartPreview() {
    val calendar = Calendar.getInstance()
    calendar[Calendar.HOUR_OF_DAY] = 0
    calendar[Calendar.MINUTE] = 0
    calendar[Calendar.SECOND] = 0
    calendar[Calendar.MILLISECOND] = 0

    val weatherPerHour = (1..10).map { index ->
        if (index != 0) {
            calendar.add(Calendar.HOUR, 1)
        }

        HourWeather(
            time = calendar.time,
            facts = WeatherFacts.Default.copy(temperature = index.toFloat()),
            night = false
        )
    }
    val item = HourlyWeather(
        weatherPerHour = weatherPerHour,
        hourlyWeatherCurvePoints = HourlyWeatherCurvePoints()
    )
    HourlyWeatherChart(item, Date(), {}, true, HourlyWeatherType.Temperature)
}