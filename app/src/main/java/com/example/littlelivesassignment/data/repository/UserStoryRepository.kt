package com.example.littlelivesassignment.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.littlelivesassignment.data.local.db.EventDatabase
import com.example.littlelivesassignment.data.model.Event
import com.example.littlelivesassignment.data.remote.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserStoryRepository @Inject constructor(
    private val service: ApiService,
    private val database: EventDatabase
) {

    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getEventsResultStream(): Flow<PagingData<Event>> {
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