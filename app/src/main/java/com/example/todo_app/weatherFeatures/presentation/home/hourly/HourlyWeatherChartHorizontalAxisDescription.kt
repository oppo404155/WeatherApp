package com.example.todo_app.weatherFeatures.presentation.home.hourly

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo_app.weatherFeatures.Domain.models.HourWeather
import com.example.todo_app.weatherFeatures.Domain.models.HourlyWeather
import com.example.todo_app.weatherFeatures.Domain.models.HourlyWeatherCurvePoints
import com.example.todo_app.weatherFeatures.Domain.models.WeatherFacts
import com.example.todo_app.weatherFeatures.Domain.weatherUseCase.HourlyWeatherCurveParameters
import java.util.*

@Composable
fun HourlyWeatherChartHorizontalAxisDescription(
    hourlyWeather: HourlyWeather,
    selectedTime: Date,
    onWeatherTimeSelected: (Date) -> Unit
) {
    val dpPerCell = HourlyWeatherCurveParameters.cellSize
    Row(
        modifier = Modifier.padding(start = dpPerCell.dp / 2, top = 8.dp)
    ) {
        hourlyWeather.weatherPerHour.forEach { item ->
            HourWeatherChartItemDescription(
                item = item,
                selectedTime = selectedTime,
                modifier = Modifier.width(dpPerCell.dp),
                onWeatherTimeSelected = onWeatherTimeSelected
            )
        }
    }
}

@Preview
@Composable
fun HourlyWeatherChartHorizontalAxisDescriptionPreview() {
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
    HourlyWeatherChartHorizontalAxisDescription(item, Date()) {}

}