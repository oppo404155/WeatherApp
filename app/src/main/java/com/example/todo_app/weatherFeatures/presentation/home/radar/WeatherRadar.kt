package com.example.todo_app.weatherFeatures.presentation.home.radar

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo_app.R
import com.example.todo_app.ui.theme.WeatherTheme
import com.example.todo_app.weatherFeatures.presentation.components.SectionHeader

@Composable
fun WeatherRadar(onShowSnackbar: (String) -> Unit) {
    val fakeDataMessage = stringResource(R.string.fake_data_message)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        SectionHeader(
            title = stringResource(R.string.weather_radar),
            subtitle = stringResource(R.string.satellite_images)
        )
        Spacer(Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .padding(horizontal = 24.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onShowSnackbar(fakeDataMessage) },
                shape = MaterialTheme.shapes.medium,
                elevation = WeatherTheme.elevations.Card
            ) {
                Image(
                    painter = painterResource(R.drawable.weather_radar_lyon),
                    contentDescription = stringResource(R.string.satellite_images),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Preview
@Composable
fun WeatherRadarPreview() {
    WeatherRadar(onShowSnackbar = {})
}