package com.example.todo_app.weatherFeatures.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo_app.weatherFeatures.Domain.models.CurrentWeather.Companion.getDefault
import com.example.todo_app.weatherFeatures.Domain.models.HourlyWeatherType
import com.example.todo_app.weatherFeatures.Domain.utiles.successOr
import com.example.todo_app.weatherFeatures.Domain.weatherUseCase.FetchHourlyWeather
import com.example.todo_app.weatherFeatures.Domain.weatherUseCase.FetchWeatherAtTime
import com.example.todo_app.weatherFeatures.Domain.weatherUseCase.FetchWeekWeather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchWeatherAtTime: FetchWeatherAtTime,
    private val fetchHourlyWeather: FetchHourlyWeather,
    private val fetchWeekWeather: FetchWeekWeather,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeViewState())
    val state: StateFlow<HomeViewState> = _state

    private val _particleAnimationIteration = MutableStateFlow(0L)
    val particleAnimationIteration: StateFlow<Long> = _particleAnimationIteration

    private val selectedWeatherTime = MutableStateFlow(Date())
    private val selectedFilter = MutableStateFlow(HourlyWeatherType.Temperature)

    var oldSelectedWeatherTime: Date = Date()

    private var job: Job? = null

    init {
        loadData()
        viewModelScope.launch(Dispatchers.Default) {
            while (true) {
                _particleAnimationIteration.value++
                delay(1L)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun loadData(userAction: Boolean = false) {
        job = viewModelScope.launch {
            combine(
                selectedWeatherTime.flatMapLatest { fetchWeatherAtTime(it) },
                selectedFilter.flatMapLatest { type ->
                    val oldPoints = state.value.hourlyWeather
                        .successOr(null)?.hourlyWeatherCurvePoints?.points.orEmpty()
                    fetchHourlyWeather(FetchHourlyWeather.Parameters(type, oldPoints))
                },
                fetchWeekWeather(Unit),
                selectedFilter
            ) { currentWeather, hourlyWeather, weekWeather, selectedFilter ->

                // Prevent the swipe refresh to replace a successful state by a progress bar
                val newCurrentWeather = currentWeather.successOr(getDefault())

                val newHourlyWeather = if (hourlyWeather.isSuccessful()) {
                    hourlyWeather
                } else {
                    state.value.hourlyWeather
                }

                val newWeekWeather = if (!userAction || weekWeather.isSuccessful()) {
                    weekWeather
                } else {
                    state.value.weekWeather
                }

                state.value.copy(
                    currentWeather = newCurrentWeather,
                    hourlyWeather = newHourlyWeather,
                    weekWeather = newWeekWeather,
                    selectedFilter = selectedFilter,
                    refreshing = if (userAction) {
                        currentWeather.isLoading() || hourlyWeather.isLoading() || weekWeather.isLoading()
                    } else {
                        false
                    }
                )
            }.collect { _state.value = it }
        }
    }

    fun refresh() {
        job?.cancel()
        _state.value = state.value.copy(refreshing = true)
        loadData(userAction = true)
    }

    fun onWeatherDateSelected(time: Date) {
        selectedWeatherTime.value = time
    }

    fun onFilterSelected(filter: HourlyWeatherType) {
        selectedFilter.value = filter
    }
}
