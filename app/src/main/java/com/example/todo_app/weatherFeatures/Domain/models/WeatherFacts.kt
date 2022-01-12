package com.example.todo_app.weatherFeatures.Domain.models

data class WeatherFacts(
    val temperature: Float,
    val apparentTemperature: Int,
    val precipitation: Float,
    val humidity: Float,
    val windSpeed: Float,
    val cloudCover: Float,
    val pressure: Float,
    val visibility: Float,
    val uvIndex: Int,
    val dewPoint: Int,
    val state: WeatherState,
) {
    companion object {
        val Default = WeatherFacts(
            temperature = 2f,
            apparentTemperature = 2,
            precipitation = 0.2f,
            humidity = 0.49f,
            windSpeed = 17f,
            cloudCover = 0.88f,
            pressure = 1.0f,
            visibility = 5f,
            uvIndex = 1,
            dewPoint = -4,
            state = WeatherState.CLEAR_SKY
        )
    }
}

