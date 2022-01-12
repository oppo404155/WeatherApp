package com.example.todo_app.weatherFeatures.Domain.DI

import com.example.todo_app.weatherFeatures.Domain.DefaultDispatcher
import com.example.todo_app.weatherFeatures.Domain.IoDispatcher
import com.example.todo_app.weatherFeatures.Domain.MainDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatchersObject {
   @MainDispatcher
    @Singleton
    @Provides
    fun provideMainDispatcher():CoroutineDispatcher=Dispatchers.Main
    @IoDispatcher
    @Singleton
    @Provides
    fun provideIoDispatchers():CoroutineDispatcher=Dispatchers.IO
   @DefaultDispatcher
    @Singleton
    @Provides
    fun provideDefaultDispatcher():CoroutineDispatcher=Dispatchers.Default

}