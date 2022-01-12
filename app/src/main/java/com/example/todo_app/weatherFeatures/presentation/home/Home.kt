package com.example.todo_app.weatherFeatures.presentation.home

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Face
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todo_app.ui.theme.BottomSheetShape
import com.example.todo_app.ui.theme.WeatherTheme
import com.example.todo_app.weatherFeatures.Domain.models.HourlyWeatherType
import com.example.todo_app.weatherFeatures.Domain.utiles.Result
import com.example.todo_app.weatherFeatures.presentation.components.ErrorMessage
import com.example.todo_app.weatherFeatures.presentation.components.SectionHeader
import com.example.todo_app.weatherFeatures.presentation.components.SectionProgressBar
import com.example.todo_app.weatherFeatures.presentation.components.SwipeToRefreshLayout
import com.example.todo_app.weatherFeatures.presentation.home.current.CurrentWeatherSection
import com.example.todo_app.weatherFeatures.presentation.home.day.DayWeather
import com.example.todo_app.weatherFeatures.presentation.home.hourly.HourlyWeather
import com.example.todo_app.weatherFeatures.presentation.home.landscape.DynamicWeatherSection
import com.example.todo_app.weatherFeatures.presentation.home.radar.WeatherRadar
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel= hiltViewModel()) {
    val viewState by viewModel.state.collectAsState()
    val scaffoldState = rememberBackdropScaffoldState(BackdropValue.Revealed)
    var snackbarMessage by remember { mutableStateOf<String?>(null) }

    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1600, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    if (snackbarMessage != null) {
        LaunchedEffect(scaffoldState) {
            val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(snackbarMessage!!)
            if (snackbarResult == SnackbarResult.Dismissed) {
                snackbarMessage = null
            }
        }
    }

    SwipeToRefreshLayout(
        refreshingState = viewState.refreshing,
        onRefresh = { viewModel.refresh() },
        refreshIndicator = {
            Surface(
                elevation = 10.dp,
                shape = CircleShape,
                color = MaterialTheme.colors.background
            ) {
                Icon(

                    imageVector = Icons.Rounded.Face,
                    contentDescription = stringResource(com.example.todo_app.R.string.refresh_layout),
                    modifier = Modifier
                        .size(36.dp)
                        .padding(4.dp)
                        .rotate(rotation),
                    tint = MaterialTheme.colors.primary
                )
            }
        },
        content = {
            BackdropScaffold(
                scaffoldState = scaffoldState,
                frontLayerScrimColor = Color.Transparent,
                backLayerBackgroundColor = Color.Transparent,
                frontLayerElevation = if (scaffoldState.isConcealed) WeatherTheme.elevations.Backdrop else 0.dp,
                frontLayerShape = BottomSheetShape,
                backLayerContent = {
                    DynamicWeatherSection(viewState.currentWeather, viewModel)
                },
                frontLayerContent = {
                    DetailedWeather(
                        viewState = viewState,
                        onShowSnackbar = { snackbarMessage = it },
                        onWeatherTimeSelected = viewModel::onWeatherDateSelected,
                        onFilterSelected = viewModel::onFilterSelected
                    )
                },
                appBar = {},
                snackbarHost = { state ->
                    SnackbarHost(
                        state,
                        snackbar = { data ->
                            Snackbar(
                                data,
                                contentColor = MaterialTheme.colors.background,
                                elevation = 1.dp
                            )
                        }
                    )
                }
            )
        },
    )
}

@Composable
fun DetailedWeather(
    viewState: HomeViewState,
    onShowSnackbar: (String) -> Unit,
    onWeatherTimeSelected: (Date) -> Unit,
    onFilterSelected: (HourlyWeatherType) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background,
    ) {
        val scrollState = rememberLazyListState()
        LazyColumn(
            state = scrollState,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp)
        ) {

            item { CurrentWeatherSection(viewState.currentWeather) }
            item {
                HourlyWeather(
                    viewState.hourlyWeather,
                    viewState.currentWeather.time,
                    viewState.selectedFilter,
                    onWeatherTimeSelected,
                    onFilterSelected
                )
            }
            item { WeatherRadar(onShowSnackbar = { onShowSnackbar(it) }) }
            item {
                SectionHeader(
                    title = stringResource(com.example.todo_app.R.string.this_week),
                    subtitle = stringResource(
                        com.example.todo_app.R.string.forecast_7days
                    )
                )
            }
            item { Spacer(Modifier.height(8.dp)) }
            when (viewState.weekWeather) {
                is Result.Error -> item { ErrorMessage() }
                Result.Loading -> item { SectionProgressBar() }
                is Result.Success -> {
                    itemsIndexed(viewState.weekWeather.data) { _,item ->
                        DayWeather(item)
                    }
                }
            }
            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}



