package com.example.littlelivesassignment.data.remote

import com.example.littlelivesassignment.data.model.UserStoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("some/path1")
    suspend fun getData(
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): UserStoryResponse

    @GET("some/path2")
    suspend fun getData(): UserStoryResponse
}