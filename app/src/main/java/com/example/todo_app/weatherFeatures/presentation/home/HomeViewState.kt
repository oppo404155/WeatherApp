package com.example.todo_app.weatherFeatures.presentation.home

import com.example.todo_app.weatherFeatures.Domain.models.CurrentWeather
import com.example.todo_app.weatherFeatures.Domain.models.DayWeather
import com.example.todo_app.weatherFeatures.Domain.models.HourlyWeather
import com.example.todo_app.weatherFeatures.Domain.models.HourlyWeatherType
import com.example.todo_app.weatherFeatures.Domain.utiles.Result

data class HomeViewState(
    val currentWeather: CurrentWeather = CurrentWeather.getDefault(),
    val hourlyWeather: Result<HourlyWeather> = Result.Loading,
    val weekWeather: Result<List<DayWeather>> = Result.Loading,
    val refreshing: Boolean = false,
    val selectedFilter: HourlyWeatherType = HourlyWeatherType.Temperature,
    )
