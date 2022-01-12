package com.example.todo_app.weatherFeatures.presentation.particle

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Particles(
    modifier: Modifier = Modifier,
    iteration: Long,
    parameters: PrecipitationsParameters
) {
    val scope= rememberCoroutineScope()
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val particleGenerator by remember {
            mutableStateOf(
                ParticleSystemHelper(
                    parameters, constraints.maxWidth, constraints.maxHeight
                )
            )
        }

        var particles by remember {
            mutableStateOf(
                listOf<Particle>()
            )
        }

        particleGenerator.generateParticles()
        particleGenerator.updateParticles(iteration)
        // Trigger recomposition
        particles = particleGenerator.particles

        Canvas(
            modifier = modifier,
            onDraw = {
                particles.forEach { particle ->
                    when (parameters.shape) {
                        is PrecipitationShape.Circle -> {
                            drawCircle(
                                color = parameters.shape.color,
                                radius = particle.width,
                                center = Offset(particle.x, particle.y)
                            )
                        }
                        is PrecipitationShape.Line -> {
                            val endX = particle.x - particle.height * cos(
                                Math.toRadians(particle.angle.toDouble())
                            ).toFloat()
                            val endY = particle.y - particle.height * sin(
                                Math.toRadians(particle.angle.toDouble())
                            ).toFloat()
                            drawLine(
                                color = parameters.shape.color,
                                pathEffect = PathEffect.cornerPathEffect(20f),
                                start = Offset(particle.x, particle.y),
                                end = Offset(endX, endY),
                                strokeWidth = particle.width
                            )
                        }
                        is PrecipitationShape.Image -> {
                            val x = particle.x.toInt()
                            val y = particle.y.toInt()
//                           scope.launch {
                               drawImage(
                                   image = parameters.shape.image,
                                   dstOffset = IntOffset(x, y),
                                   dstSize = IntSize(particle.width.toInt(), particle.height.toInt()),
                                   colorFilter = parameters.shape.colorFilter
                               )
//                           }

                        }
                    }
                }
            }
        )
    }
}

@Preview
@Composable
fun ParticlesPreview() {
    Particles(iteration = 1, parameters = snowParameters)
}
