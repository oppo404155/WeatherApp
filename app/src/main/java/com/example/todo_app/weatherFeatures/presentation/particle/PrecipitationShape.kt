package com.example.todo_app.weatherFeatures.presentation.particle

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap

sealed class PrecipitationShape {

    data class Circle(
        val minRadius: Int,
        val maxRadius: Int,
        val color: Color,
    ) : PrecipitationShape()

    data class Line(
        val minStrokeWidth: Int,
        val maxStrokeWidth: Int,
        val minHeight: Int,
        val maxHeight: Int,
        val color: Color,
    ) : PrecipitationShape()

    data class Image(
        val image: ImageBitmap,
        val minWidth: Int,
        val maxWidth: Int,
        val minHeight: Int,
        val maxHeight: Int,
        val colorFilter: ColorFilter,
    ) : PrecipitationShape()
}
