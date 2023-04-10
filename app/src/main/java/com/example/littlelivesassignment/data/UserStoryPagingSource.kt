package com.example.littlelivesassignment.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.littlelivesassignment.data.model.Event
import com.example.littlelivesassignment.data.remote.ApiService

class UserStoryPagingSource(
    private val service: ApiService
) : PagingSource<Int, Event>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Event> {
        TODO("Not yet implemented")
    }
    override fun getRefreshKey(state: PagingState<Int, Event>): Int? {
        TODO("Not yet implemented")
    }

}