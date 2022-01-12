package com.example.todo_app.weatherFeatures.presentation

import android.graphics.Color
import android.view.Window
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.example.todo_app.ui.theme.WeatherTheme
import com.example.todo_app.weatherFeatures.presentation.navigation.LocalBackDispatcher
import com.example.todo_app.weatherFeatures.presentation.navigation.NavGraph

@Composable
fun App(backDispatcher: OnBackPressedDispatcher, window: Window) {
    CompositionLocalProvider(LocalBackDispatcher provides backDispatcher) {
        BarsTheming(window)
            WeatherTheme {
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    NavGraph()
                }
            }
        }
    }


@Composable
fun BarsTheming(window: Window) {
    window.statusBarColor = Color.TRANSPARENT
    window.navigationBarColor = Color.TRANSPARENT
}