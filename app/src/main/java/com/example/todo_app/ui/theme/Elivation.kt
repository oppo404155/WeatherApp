package com.example.todo_app.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Elevations(
    val Backdrop: Dp = 8.dp,
    val Card: Dp = 4.dp,
)

internal val LocalElevations = staticCompositionLocalOf { Elevations() }