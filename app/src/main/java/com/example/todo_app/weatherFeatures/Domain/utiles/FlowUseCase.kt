package com.example.todo_app.weatherFeatures.Domain.utiles

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.navigation.compose.rememberNavController
import com.example.todo_app.R
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

abstract class FlowUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    operator fun invoke(parameters: P): Flow<Result<R>> {
        return execute(parameters)
            .catch { e ->
                emit(Result.Error(Exception(e)))
            }.flowOn(coroutineDispatcher)

    }

    abstract fun execute(parameters: P): Flow<Result<R>>

}
