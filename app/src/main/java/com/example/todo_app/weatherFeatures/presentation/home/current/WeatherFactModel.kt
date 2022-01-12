package com.example.todo_app.weatherFeatures.presentation.home.current

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Face
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.todo_app.weatherFeatures.Domain.models.CurrentWeather
import com.example.todo_app.weatherFeatures.Domain.models.DayWeather

data class WeatherFact(
    val label: String,
    val value: String,
    val icon: ImageVector,
)

fun CurrentWeather.extractFacts() = listOf(
    WeatherFact(
        label = "Temperature",
        value = "$maxTemperature°  |  $minTemperature°",
        icon = Icons.Outlined.Face
    ),
    WeatherFact(
        label = "Feels like",
        value = "${hourWeather.apparentTemperature}°",
        icon = Icons.Outlined.Face
    ),
    WeatherFact(
        label = "Wind speed",
        value = "${hourWeather.windSpeed.toInt()} km/h",
        icon = Icons.Rounded.Face
    ),
    WeatherFact(
        label = "Precipitation",
        value = "${(hourWeather.precipitation * 100).toInt()}%",
        icon = Icons.Outlined.Face
    ),
    WeatherFact(
        label = "Cloud cover",
        value = "${(hourWeather.cloudCover * 100).toInt()}%",
        icon = Icons.Outlined.Face
    ),
    WeatherFact(
        label = "Humidity",
        value = "${(hourWeather.humidity * 100).toInt()}%",
        icon = Icons.Outlined.Face
    ),
    WeatherFact(
        label = "Pressure",
        value = "${hourWeather.pressure} bar",
        icon = Icons.Outlined.Face
    ),
    WeatherFact(
        label = "UV Index",
        value = "${hourWeather.uvIndex}",
        icon = Icons.Outlined.Done
    ),
    WeatherFact(
        label = "Visibility",
        value = "${hourWeather.visibility} km",
        icon = Icons.Outlined.Face
    ),
    WeatherFact(
        label = "Dew Point",
        value = "${hourWeather.dewPoint}°",
        icon = Icons.Outlined.Face
    ),
    WeatherFact(
        label = "Sunrise",
        value = sunrise,
        icon = Icons.Outlined.Face
    ),
    WeatherFact(
        label = "Sunset",
        value = sunset,
        icon = Icons.Outlined.Face
    ),
)

fun DayWeather.extractFacts() = listOf(
    WeatherFact(
        label = "Cloud cover",
        value = "${(facts.cloudCover * 100).toInt()}%",
        icon = Icons.Outlined.Face
    ),
    WeatherFact(
        label = "Wind speed",
        value = "${facts.windSpeed.toInt()} km/h",
        icon = Icons.Rounded.Face
    ),
    WeatherFact(
        label = "Humidity",
        value = "${(facts.humidity * 100).toInt()}%",
        icon = Icons.Outlined.AccountBox
    ),
    WeatherFact(
        label = "UV Index",
        value = "${facts.uvIndex}",
        icon = Icons.Outlined.Face
    ),
    WeatherFact(
        label = "Sunrise",
        value = sunrise,
        icon = Icons.Outlined.Face
    ),
    WeatherFact(
        label = "Sunset",
        value = sunset,
        icon = Icons.Outlined.Face
    ),
)