package com.example.todo_app.weatherFeatures.presentation.home.day

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo_app.R
import com.example.todo_app.weatherFeatures.Domain.models.DayWeather
import com.example.todo_app.weatherFeatures.Domain.models.WeatherFacts
import com.example.todo_app.weatherFeatures.presentation.home.current.WeatherFact
import com.example.todo_app.weatherFeatures.presentation.home.current.extractFacts
import com.example.todo_app.weatherFeatures.presentation.util.getIconRes
import com.example.todo_app.weatherFeatures.presentation.util.getWeatheStateDes
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
@Composable
fun DayWeather(item: DayWeather) {
    val dateFormat = SimpleDateFormat("EEEE d")
    var expanded by rememberSaveable { mutableStateOf(false) }
    val rotation: Float by animateFloatAsState(if (expanded) 180f else 0f)

    Column(modifier = Modifier.animateContentSize()) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(horizontal = 24.dp, vertical = 20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(item.facts.state.getIconRes()),
                    contentDescription = stringResource(id = item.facts.state.getWeatheStateDes()),
                    modifier = Modifier.size(56.dp)
                )
                Spacer(Modifier.width(16.dp))
                Column(
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = dateFormat.format(item.date).capitalize(),
                        style = MaterialTheme.typography.h2,
                    )
                    Spacer(Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.Face,
                            contentDescription = stringResource(R.string.temperature),
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colors.primary
                        )
                        Spacer(Modifier.width(4.dp))
                        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                            Text(
                                text = "${item.maxTemperature}°  |  ${item.minTemperature}°",
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier
                            )
                        }
                        Spacer(Modifier.width(16.dp))
                        Icon(
                            painter = painterResource(R.drawable.ic_rain),
                            contentDescription = stringResource(R.string.precipitation),
                            modifier = Modifier.size(22.dp),
                            tint = MaterialTheme.colors.primary
                        )
                        Spacer(Modifier.width(4.dp))
                        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                            Text(
                                text = "${item.facts.precipitation}%",
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier
                            )
                        }
                    }
                }
            }

            Icon(
                imageVector = Icons.Rounded.ArrowDropDown,
                contentDescription = stringResource(id = R.string.expandable_header_toggle_message),
                modifier = Modifier
                    .size(28.dp)
                    .rotate(rotation),
            )
        }

        if (expanded) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(LocalContentColor.current.copy(alpha = 0.06f))
            ) {
                Text(
                    text = stringResource(id = item.facts.state.getWeatheStateDes()),
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp)
                )

                val weatherFacts = item.extractFacts()

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
    }
}

@Preview
@Composable
fun DayWeatherPreview() {
    val item = DayWeather(
        date = Date(),
        facts = WeatherFacts.Default,
        sunrise = "06:44",
        sunset = "18:54",
        minTemperature = 4,
        maxTemperature = 21,
    )

    DayWeather(item)
}
