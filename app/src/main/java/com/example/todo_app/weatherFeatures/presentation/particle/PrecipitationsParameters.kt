package com.example.todo_app.weatherFeatures.presentation.particle

import androidx.compose.ui.graphics.Color

data class PrecipitationsParameters(
    val particleCount: Int,
    val distancePerStep: Int,
    val minSpeed: Float,
    val maxSpeed: Float,
    val minAngle: Int,
    val maxAngle: Int,
    val shape: PrecipitationShape,
    val sourceEdge: PrecipitationSourceEdge
)

val snowParameters = PrecipitationsParameters(
    particleCount = 200,
    distancePerStep = 5,
    minSpeed = 0.1f,
    maxSpeed = 1f,
    minAngle = 260,
    maxAngle = 280,
    shape = PrecipitationShape.Circle(
        minRadius = 1,
        maxRadius = 10,
        color = Color.White,
    ),
    sourceEdge = PrecipitationSourceEdge.TOP
)

val rainParameters = PrecipitationsParameters(
    particleCount = 600,
    distancePerStep = 30,
    minSpeed = 0.7f,
    maxSpeed = 1f,
    minAngle = 265,
    maxAngle = 285,
    shape = PrecipitationShape.Line(
        minStrokeWidth = 1,
        maxStrokeWidth = 3,
        minHeight = 10,
        maxHeight = 15,
        color = Color.Gray,
    ),
    sourceEdge = PrecipitationSourceEdge.TOP
)
