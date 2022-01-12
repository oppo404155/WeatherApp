package com.example.todo_app.weatherFeatures.presentation.home.current

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.todo_app.weatherFeatures.Domain.models.CurrentWeather
import com.example.todo_app.weatherFeatures.Domain.models.WeatherFacts
import com.example.todo_app.R
import com.example.todo_app.weatherFeatures.presentation.components.ExpandableSectionHeader

@Composable
fun CurrentWeatherSection(currentWeather: CurrentWeather) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        ExpandableSectionHeader(
            title = stringResource(R.string.details),
            subtitle = stringResource(R.string.weather_now),
            expanded = expanded,
            onToggleState = { expanded = !expanded }
        )
        Spacer(Modifier.height(8.dp))

        val weatherFacts = currentWeather.extractFacts()

        val itemsPerRow = 2
        weatherFacts.chunked(itemsPerRow)
            .run { if (!expanded) take(2) else this }
            .forEach { factsPerRow ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    factsPerRow.forEachIndexed { index, fact ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(1f / (itemsPerRow - index))
                        ) {
                            WeatherFact(fact)
                        }
                    }
                }
            }
    }
}
