package com.example.littlelivesassignment.di

import android.app.Application
import com.example.littlelivesassignment.data.local.deserializer.EventSnapshotDeserializer
import com.example.littlelivesassignment.data.local.deserializer.UserEventDeserializer
import com.example.littlelivesassignment.data.model.EventSnapshot
import com.example.littlelivesassignment.data.model.UserEvent
import com.example.littlelivesassignment.data.remote.ApiService
import com.example.littlelivesassignment.data.remote.MockDataInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(application: Application): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(MockDataInterceptor(application, "mock_data.json"))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.example.com") // This can be any URL, it's not used in this case
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val builder = GsonBuilder()
        //register type adapter
        builder
            .registerTypeAdapter(UserEvent::class.java, UserEventDeserializer())
//            .registerTypeAdapter(EventSnapshot::class.java, EventSnapshotDeserializer())
        return builder.create()
    }
}