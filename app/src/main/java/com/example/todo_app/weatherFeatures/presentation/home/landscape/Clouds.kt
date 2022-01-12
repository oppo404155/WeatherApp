package com.example.todo_app.weatherFeatures.presentation.home.landscape

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo_app.R
import com.example.todo_app.weatherFeatures.presentation.particle.Particles
import com.example.todo_app.weatherFeatures.presentation.particle.PrecipitationShape
import com.example.todo_app.weatherFeatures.presentation.particle.PrecipitationSourceEdge
import com.example.todo_app.weatherFeatures.presentation.particle.PrecipitationsParameters


@Composable
fun Clouds(
    modifier: Modifier = Modifier,
    tint: ColorFilter,
    particleAnimationIteration: Long,
    cloudCount: Int
) {
    BoxWithConstraints(modifier = modifier) {
        val cloudsParameters = PrecipitationsParameters(
            particleCount = cloudCount,
            distancePerStep = 1,
            minSpeed = 0.2f,
            maxSpeed = 0.6f,
            minAngle = 0,
            maxAngle = 0,
            shape = PrecipitationShape.Image(
                image = ImageBitmap.imageResource(R.drawable.cloud),
                minWidth = with(LocalDensity.current) { 60.dp.toPx() }.toInt(),
                maxWidth = with(LocalDensity.current) { 120.dp.toPx() }.toInt(),
                minHeight = with(LocalDensity.current) { 30.dp.toPx() }.toInt(),
                maxHeight = with(LocalDensity.current) { 60.dp.toPx() }.toInt(),
                colorFilter = tint

            ),
            sourceEdge = PrecipitationSourceEdge.RIGHT
        )

        Particles(
            modifier = Modifier
                .fillMaxSize(),
            iteration = particleAnimationIteration,
            parameters = cloudsParameters
        )
    }
}

@Preview
@Composable
fun CloudsPreview() {
    Clouds(
        tint = ColorFilter.tint(
            Color.Black.copy(alpha = 0.2f),
            BlendMode.SrcAtop
        ),
        particleAnimationIteration = 1,
        cloudCount = 8
    )
}