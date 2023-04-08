package com.example.littlelivesassignment.data.remote

import com.example.littlelivesassignment.data.model.EventData
import retrofit2.http.GET

interface ApiService {
    @GET("some/path")
    suspend fun getData(): EventData
}