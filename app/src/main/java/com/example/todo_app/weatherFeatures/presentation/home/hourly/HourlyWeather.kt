package com.example.todo_app.weatherFeatures.presentation.home.hourly

import HourlyWeatherFilters
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.todo_app.R
import com.example.todo_app.weatherFeatures.Domain.models.HourlyWeather
import com.example.todo_app.weatherFeatures.Domain.models.HourlyWeatherType
import com.example.todo_app.weatherFeatures.presentation.components.ErrorMessage
import com.example.todo_app.weatherFeatures.presentation.components.ExpandableSectionHeader
import com.example.todo_app.weatherFeatures.presentation.components.SectionProgressBar
import java.util.*
import com.example.todo_app.weatherFeatures.Domain.utiles.Result

@Composable
fun HourlyWeather(
    hourlyWeatherResult: Result<HourlyWeather>,
    selectedTime: Date,
    selectedFilter: HourlyWeatherType,
    onWeatherTimeSelected: (Date) -> Unit,
    onFilterSelected: (HourlyWeatherType) -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(true) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        ExpandableSectionHeader(
            title = stringResource(R.string.hourly_weather),
            subtitle = stringResource(R.string.forecast_24h),
            expanded = expanded,
            onToggleState = { expanded = !expanded }
        )
        Spacer(Modifier.height(8.dp))

        when (hourlyWeatherResult) {
            is Result.Error -> ErrorMessage()
            Result.Loading -> SectionProgressBar()
            is Result.Success -> {
                HourlyWeatherFilters(selectedFilter, onFilterSelected)
                HourlyWeatherChart(
                    hourlyWeatherResult.data,
                    selectedTime,
                    onWeatherTimeSelected,
                    expanded,
                    selectedFilter
                )
            }
        }
    }
}
