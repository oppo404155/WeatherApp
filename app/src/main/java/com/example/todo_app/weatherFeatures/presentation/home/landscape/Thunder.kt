package com.example.todo_app.weatherFeatures.presentation.home.landscape

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo_app.weatherFeatures.Domain.models.Point
import kotlin.random.Random

@Composable
fun Thunder(particleAnimationIteration: Long, width: Int, height: Int) {
    val thunderPath by remember { mutableStateOf(ThunderPath()) }
    val thunderIteration = (particleAnimationIteration / THUNDER_PHASE).toInt()

    val centerX = width / 2f
    val centerY = height / 2f
    if (thunderPath.id != thunderIteration) {
        val newPoints = mutableListOf<Point>()
        thunderPath.id = thunderIteration
        val pointCount = Random.nextInt(THUNDER_POINT_COUNT_MIN, THUNDER_POINT_COUNT_MAX).toFloat()
        val startX = Random.nextInt(width).toFloat()
        val startY = Random.nextInt((centerY / 2).toInt()).toFloat()

        newPoints.add(Point(startX, startY))

        while (newPoints.size < pointCount - 2) {
            val offsetX = Random.nextInt(THUNDER_PROGRESS_X_MIN, THUNDER_PROGRESS_X_MAX).toFloat()
            val offsetY = Random.nextInt(THUNDER_PROGRESS_Y_MIN, THUNDER_PROGRESS_Y_MAX).toFloat()
            val lastPoint = newPoints.last()

            newPoints.add(Point(lastPoint.x + offsetX, lastPoint.y + offsetY))
        }

        newPoints.add(Point(centerX, height.toFloat()))
        thunderPath.points = newPoints
    }

    if (particleAnimationIteration % THUNDER_PHASE < THUNDER_DURATION) {
        Canvas(
            modifier = Modifier.fillMaxSize(),
            onDraw = {
                val path = Path()
                thunderPath.points.forEachIndexed { index, point ->
                    if (index == 0) {
                        path.moveTo(point.x, point.y)
                    } else {
                        path.lineTo(point.x, point.y)
                    }
                }
                drawPath(path, Color.White, style = Stroke(width = 2.dp.toPx()))
            }
        )
    }
}

@Preview
@Composable
fun ThunderPreview() {
    Thunder(
        particleAnimationIteration = 1,
        width = 1080,
        height = 500
    )
}

data class ThunderPath(
    var id: Int = 0,
    var points: List<Point> = emptyList()
)

private const val THUNDER_POINT_COUNT_MIN = 4
private const val THUNDER_POINT_COUNT_MAX = 8
private const val THUNDER_PROGRESS_X_MIN = -200
private const val THUNDER_PROGRESS_X_MAX = 200
private const val THUNDER_PROGRESS_Y_MIN = 50
private const val THUNDER_PROGRESS_Y_MAX = 200
private const val THUNDER_PHASE = 4000
private const val THUNDER_DURATION = 200