package com.example.todo_app.weatherFeatures.Data.weatheRrepoImplem

import com.example.todo_app.weatherFeatures.Data.dataSource.FakeWeatherDataSource
import com.example.todo_app.weatherFeatures.Domain.models.CurrentWeather
import com.example.todo_app.weatherFeatures.Domain.models.DayWeather
import com.example.todo_app.weatherFeatures.Domain.models.HourWeather
import com.example.todo_app.weatherFeatures.Domain.weatherRepo.WeatherRepository
import kotlinx.coroutines.delay
import java.util.*

class WeatherRepoImp constructor(val dataSource: FakeWeatherDataSource) :
    WeatherRepository {
    override suspend fun fetchWeatherAtTime(time: Date): CurrentWeather {
        return FakeWeatherDataSource.fetchWeatherAtTime(time)
    }

    override suspend fun fetchHourlyWeather(): List<HourWeather> {
       return dataSource.getNext24HoursWeather()
    }

    override suspend fun fetchWeekWeather(): List<DayWeather> {
        //simulate network delay
        delay(400)
        return dataSource.getNext7dayWeather()
    }
}