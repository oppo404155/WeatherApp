package com.example.todo_app.weatherFeatures.Domain.models

import java.util.*

data class CurrentWeather(
    val time: Date,
    val hourWeather: WeatherFacts,
    val sunrise: String,
    val sunset: String,
    val minTemperature: Int,
    val maxTemperature: Int,
) {
    companion object {

        fun getDefault(): CurrentWeather {
            val calendar = Calendar.getInstance()
            calendar[Calendar.MINUTE] = 0
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0

            return CurrentWeather(
                time = calendar.time,
                hourWeather = WeatherFacts.Default,
                sunrise = "06:46",
                sunset = "18:53",
                minTemperature = -1,
                maxTemperature = 9
            )
        }
    }
}
