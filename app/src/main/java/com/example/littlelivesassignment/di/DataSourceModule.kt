package com.example.littlelivesassignment.di

import com.example.littlelivesassignment.data.remote.ApiService
import com.example.littlelivesassignment.data.source.UserEventDataSource
import com.example.littlelivesassignment.data.source.UserEventDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataSourceModule {

    @Provides
    @Singleton
    fun provideUserEventDataSource(apiService: ApiService): UserEventDataSource {
        return UserEventDataSourceImpl(apiService)
    }
}