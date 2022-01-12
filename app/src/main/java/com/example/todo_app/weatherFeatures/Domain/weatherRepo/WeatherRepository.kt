package com.example.todo_app.weatherFeatures.Domain.weatherRepo

import com.example.todo_app.weatherFeatures.Domain.models.CurrentWeather
import com.example.todo_app.weatherFeatures.Domain.models.DayWeather
import com.example.todo_app.weatherFeatures.Domain.models.HourWeather
import java.util.*

interface WeatherRepository {
    suspend fun fetchWeatherAtTime(time: Date): CurrentWeather
    suspend fun fetchHourlyWeather(): List<HourWeather>
    suspend fun fetchWeekWeather(): List<DayWeather>
}