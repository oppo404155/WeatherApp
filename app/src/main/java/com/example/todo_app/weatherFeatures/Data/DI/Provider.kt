package com.example.todo_app.weatherFeatures.Data.DI

import com.example.todo_app.weatherFeatures.Data.dataSource.FakeWeatherDataSource
import com.example.todo_app.weatherFeatures.Data.weatheRrepoImplem.WeatherRepoImp
import com.example.todo_app.weatherFeatures.Domain.weatherRepo.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Provider {
    @Singleton
    @Provides
    fun provideDataSource() = FakeWeatherDataSource

    @Singleton
    @Provides
    fun provideWeatherRepo(dataSource: FakeWeatherDataSource): WeatherRepository =
        WeatherRepoImp(dataSource)
}