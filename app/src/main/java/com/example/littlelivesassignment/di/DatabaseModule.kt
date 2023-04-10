package com.example.littlelivesassignment.di

import android.content.Context
import com.example.littlelivesassignment.data.local.dao.EventDao
import com.example.littlelivesassignment.data.local.dao.RemoteKeysDao
import com.example.littlelivesassignment.data.local.db.EventDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): EventDatabase {
        return EventDatabase.getInstance(context)
    }

    @Provides
    fun provideEventDao(appDatabase: EventDatabase): EventDao {
        return appDatabase.eventDao()
    }

    @Provides
    fun provideRemoteKeysDao(appDatabase: EventDatabase): RemoteKeysDao {
        return appDatabase.remoteKeysDao()
    }
}