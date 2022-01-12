package com.example.todo_app.weatherFeatures.Domain.models

data class HourlyWeatherCurvePoints(
    val points: List<Point> = emptyList(),
    val connectionPoints1: List<Point> = emptyList(),
    val connectionPoints2: List<Point> = emptyList(),

)
