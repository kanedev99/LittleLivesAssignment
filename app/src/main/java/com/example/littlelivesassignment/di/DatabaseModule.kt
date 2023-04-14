package com.example.littlelivesassignment.di

import android.content.Context
import com.example.littlelivesassignment.data.local.dao.EventDao
import com.example.littlelivesassignment.data.local.dao.RemoteKeysDao
import com.example.littlelivesassignment.data.local.db.UserEventDatabase
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
    fun provideAppDatabase(@ApplicationContext context: Context): UserEventDatabase {
        return UserEventDatabase.getInstance(context)
    }

    @Provides
    fun provideEventDao(appDatabase: UserEventDatabase): EventDao {
        return appDatabase.eventDao()
    }

    @Provides
    fun provideRemoteKeysDao(appDatabase: UserEventDatabase): RemoteKeysDao {
        return appDatabase.remoteKeysDao()
    }
}