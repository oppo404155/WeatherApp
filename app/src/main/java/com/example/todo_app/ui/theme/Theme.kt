package com.example.todo_app.ui.theme

import android.content.res.Resources
import android.provider.MediaStore
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val LightTheme = lightColors(
    primary = yellow500,
    secondary = rust600,
    onSecondary = white,
    onPrimary = white,
    surface = white850,
    background = white,
    onSurface = gray800
)

private val DarkTheme = darkColors(
    primary = yellow,
    secondary = rust300,
    onSecondary = gray900,
    onPrimary = gray900,
    surface = white150,
    background = gray900,
    onSurface = white800
)

private val LightImages = Images(lockupLogo =com.example.todo_app.R.drawable.ic_sunny)

private val DarkImages = Images(lockupLogo = com.example.todo_app.R.drawable.ic_night_clear)

@Composable
fun WeatherTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkTheme
    } else {
        LightTheme
    }

    val images = if (darkTheme) DarkImages else LightImages
    CompositionLocalProvider(
        LocalElevations provides Elevations(),
        LocalImages provides images
    ) {
        MaterialTheme(
            colors = colors,
            typography = typography,
            shapes = shapes,
            content = content
        )
    }
}

/**
 * Alternate to [MaterialTheme] allowing us to add our own theme systems (e.g. [Elevations]) or to
 * extend [MaterialTheme]'s types e.g. return our own [Colors] extension
 */
object WeatherTheme {

    /**
     * Proxy to [MaterialTheme]
     */
    val colors: Colors
        @Composable
        get() = MaterialTheme.colors

    /**
     * Proxy to [MaterialTheme]
     */
    val typography: Typography
        @Composable
        get() = MaterialTheme.typography

    /**
     * Proxy to [MaterialTheme]
     */
    val shapes: Shapes
        @Composable
        get() = MaterialTheme.shapes

    /**
     * Retrieves the current [Elevations] at the call site's position in the hierarchy.
     */
    val elevations: Elevations
        @Composable
        get() = LocalElevations.current

    /**
     * Retrieves the current [Images] at the call site's position in the hierarchy.
     */
    val images:Images
        @Composable
        get() = LocalImages.current
}
