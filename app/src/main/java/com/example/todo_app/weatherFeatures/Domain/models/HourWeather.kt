package com.example.todo_app.weatherFeatures.Domain.models

import java.util.*

data class HourWeather(
    val time: Date,
    val facts: WeatherFacts,
    val night: Boolean
)