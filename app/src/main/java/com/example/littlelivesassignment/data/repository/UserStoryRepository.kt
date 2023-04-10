package com.example.littlelivesassignment.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.littlelivesassignment.data.local.db.EventDatabase
import com.example.littlelivesassignment.data.model.Event
import com.example.littlelivesassignment.data.remote.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val TAG = "UserStoryRepository"
class UserStoryRepository @Inject constructor(
    private val service: ApiService,
    private val database: EventDatabase
) {

    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getEventsResult(): Flow<PagingData<Event>> {
        Log.d(TAG, "getEventsResult: Entry")
        val pagingSourceFactory = { database.eventDao().getEventList() }

        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = UserStoryRemoteMediator(
                service,
                database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

}