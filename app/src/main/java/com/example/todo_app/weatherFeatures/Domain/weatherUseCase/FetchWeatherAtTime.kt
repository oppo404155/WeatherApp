package com.example.todo_app.weatherFeatures.Domain.weatherUseCase

import com.example.todo_app.weatherFeatures.Domain.IoDispatcher
import com.example.todo_app.weatherFeatures.Domain.models.CurrentWeather
import com.example.todo_app.weatherFeatures.Domain.utiles.FlowUseCase
import com.example.todo_app.weatherFeatures.Domain.utiles.Result
import com.example.todo_app.weatherFeatures.Domain.weatherRepo.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

class FetchWeatherAtTime @Inject constructor(
    private val repository: WeatherRepository,
    @IoDispatcher  dispatcher: CoroutineDispatcher
) : FlowUseCase<Date, CurrentWeather>(dispatcher) {
    override fun execute(parameters: Date): Flow<Result<CurrentWeather>> {
        return flow{
            emit(Result.Loading)
            emit(Result.Success(repository.fetchWeatherAtTime(parameters)))

        }
    }
}