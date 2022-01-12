package com.example.todo_app.weatherFeatures.Domain.weatherUseCase

import com.example.todo_app.weatherFeatures.Domain.IoDispatcher
import com.example.todo_app.weatherFeatures.Domain.models.*
import com.example.todo_app.weatherFeatures.Domain.utiles.FlowUseCase
import com.example.todo_app.weatherFeatures.Domain.utiles.Result
import com.example.todo_app.weatherFeatures.Domain.weatherRepo.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Math.*
import javax.inject.Inject

class FetchHourlyWeather @Inject constructor(
    private val repository: WeatherRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<FetchHourlyWeather.Parameters, HourlyWeather>(dispatcher) {

    override fun execute(parameters: Parameters): Flow<Result<HourlyWeather>> {
        return flow {
            emit(Result.Loading)
            val weatherPerHour = repository.fetchHourlyWeather()
            val hourlyWeatherCurvePoints = computeHourlyWeatherCurvePoints(
                hourlyWeather = weatherPerHour,
                parameters.type,
            )

            val newPoints = hourlyWeatherCurvePoints.points
            val oldPoints = if (parameters.oldPoints.isNotEmpty()) {
                parameters.oldPoints
            } else {
                newPoints.map { it.copy(y = 0f) }
            }

            val size = oldPoints.size
            var maxDiffY = 0f
            for (i in 0 until size) {
                val abs = abs(oldPoints[i].y - newPoints[i].y)
                if (abs > maxDiffY) maxDiffY = abs
            }

            val loopCount = maxDiffY / 16
            val tempPointsForAnimation = mutableListOf<MutableList<Point>>()

            for (i in 0 until size) {
                val old = oldPoints[i]
                val new = newPoints[i]

                val plusOrMinusAmount = abs(new.y - old.y) / maxDiffY * 16

                var tempY = old.y
                val tempList = mutableListOf<Point>()

                for (j in 0..loopCount.toInt()) {
                    if (tempY == new.y) {
                        tempList.add(Point(new.x, new.y))
                    } else {
                        if (new.y > old.y) {
                            tempY += plusOrMinusAmount
                            tempY = min(tempY, new.y)
                            tempList.add(Point(new.x, tempY))
                        } else {
                            tempY -= plusOrMinusAmount
                            tempY = max(tempY, new.y)
                            tempList.add(Point(new.x, tempY))
                        }
                    }
                }
                tempPointsForAnimation.add(tempList)
            }

            val first = tempPointsForAnimation[0]
            val length = first.size

            for (i in 0 until length) {
                emit(
                    Result.Success(
                        HourlyWeather(
                            weatherPerHour = weatherPerHour,
                            hourlyWeatherCurvePoints = computeConnectionPoints(
                                tempPointsForAnimation.map { it[i] }.toMutableList()
                            )
                        )
                    )
                )
                delay(16)
            }
        }
    }

    // Compute bezier curve points in IO thread
    private fun computeHourlyWeatherCurvePoints(
        hourlyWeather: List<HourWeather>,
        type: HourlyWeatherType,
    ): HourlyWeatherCurvePoints {
        val cellSize = HourlyWeatherCurveParameters.cellSize
        val offsetY = HourlyWeatherCurveParameters.offsetTop
        val chartHeight = HourlyWeatherCurveParameters.heightInterval
        val chartTopPadding = HourlyWeatherCurveParameters.offsetTop
        val curveBottomOffset = HourlyWeatherCurveParameters.offsetBottom
        val minY = hourlyWeather.minOf { getValueForType(it, type) }
        val maxY = hourlyWeather.maxOf { getValueForType(it, type) }
        val heightStep =
            (chartHeight - (chartTopPadding + curveBottomOffset)) / (maxY - minY)

        val points = hourlyWeather.mapIndexed { index, item ->
            Point(
                (index.toFloat() + 1) * cellSize,
                (maxY - getValueForType(item, type)) * heightStep + offsetY
            )
        }.toMutableList()
        points.add(0, points.first().copy(x = 0f))
        val lastPoint = points.last()
        points.add(lastPoint.copy(x = lastPoint.x + cellSize))

        return computeConnectionPoints(points)
    }

    private fun computeConnectionPoints(points: MutableList<Point>): HourlyWeatherCurvePoints {
        val connectionPoints1 = mutableListOf<Point>()
        val connectionPoints2 = mutableListOf<Point>()

        try {
            for (i in 1 until points.size) {
                connectionPoints1.add(
                    Point(
                        (points[i].x + points[i - 1].x) / 2,
                        points[i - 1].y
                    )
                )
                connectionPoints2.add(
                    Point(
                        (points[i].x + points[i - 1].x) / 2,
                        points[i].y
                    )
                )
            }
        } catch (e: Exception) {
        }

        return HourlyWeatherCurvePoints(
            points,
            connectionPoints1,
            connectionPoints2
        )
    }

    private fun getValueForType(weather: HourWeather, type: HourlyWeatherType): Float {
        return when (type) {
            HourlyWeatherType.Temperature -> weather.facts.temperature
            HourlyWeatherType.Wind -> weather.facts.windSpeed
            HourlyWeatherType.CloudCover -> weather.facts.cloudCover * 100
        }
    }

    data class Parameters(
        val type: HourlyWeatherType,
        val oldPoints: List<Point>
    )
}

object HourlyWeatherCurveParameters {
    const val cellSize = 72
    const val offsetTop = 56
    const val offsetBottom = 32
    const val heightInterval = 320
}