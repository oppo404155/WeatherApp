package com.example.todo_app.weatherFeatures.Domain.models

import java.util.*

data class DayWeather(
    val date: Date,
    val facts: WeatherFacts,
    val sunrise: String,
    val sunset: String,
    val minTemperature: Int,
    val maxTemperature: Int,
)
