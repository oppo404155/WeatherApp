package com.example.todo_app.weatherFeatures.Domain.models

data class HourlyWeather(
    val weatherPerHour: List<HourWeather>,
    val hourlyWeatherCurvePoints: HourlyWeatherCurvePoints,
)
