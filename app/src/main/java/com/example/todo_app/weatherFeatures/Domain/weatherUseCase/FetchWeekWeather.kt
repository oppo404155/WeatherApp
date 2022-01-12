package com.example.todo_app.weatherFeatures.Domain.weatherUseCase

import com.example.todo_app.weatherFeatures.Domain.IoDispatcher
import com.example.todo_app.weatherFeatures.Domain.models.DayWeather
import com.example.todo_app.weatherFeatures.Domain.utiles.FlowUseCase
import com.example.todo_app.weatherFeatures.Domain.utiles.Result
import com.example.todo_app.weatherFeatures.Domain.weatherRepo.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchWeekWeather @Inject constructor(
    private val repository: WeatherRepository,
    @IoDispatcher dispatcher:CoroutineDispatcher
    ):FlowUseCase<Unit,List<DayWeather>>(dispatcher) {
    override fun execute(parameters: Unit): Flow<Result<List<DayWeather>>> {
        return flow {
            emit(Result.Loading)
            emit(Result.Success(repository.fetchWeekWeather()))
        }
    }
}