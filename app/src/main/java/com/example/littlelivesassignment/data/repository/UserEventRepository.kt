package com.example.littlelivesassignment.data.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.littlelivesassignment.adapter.EventListAdapter
import com.example.littlelivesassignment.data.model.EventType
import com.example.littlelivesassignment.data.model.UserEvent
import com.example.littlelivesassignment.data.remote.ApiService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserEventRepository @Inject constructor(
    private val service: ApiService
) : PagingSource<Int, UserEvent>() {

    companion object {
                const val NETWORK_PAGE_SIZE     = 10
        private const val STARTING_PAGE_INDEX   = 1
    }

    override fun getRefreshKey(state: PagingState<Int, UserEvent>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserEvent> {
        val pageIndex = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = service.getData(
                page = pageIndex,
                itemsPerPage = 3
            )
            val userEvents = response.data.userTimeline
                .filterNot { it.type == EventType.ACTIVITY || it.type == EventType.PORTFOLIO}

            val eventsByDate = HashMap<String, MutableList<UserEvent>>()
            val arrayData = arrayListOf<UserEvent>()

            for (event in userEvents) {
                val dateString = event.date?.substring(0, 10)?: ""
                if (!eventsByDate.containsKey(dateString)) {
                    eventsByDate[dateString] = mutableListOf()
                    arrayData.add(UserEvent(type = EventType.HEADER, date = dateString))
                }
                eventsByDate[dateString]?.add(event)
                arrayData.add(event)
            }

            Log.d("Kane", "load: events size = ${userEvents.size}")
            val nextKey =
                if (userEvents.isEmpty()) {
                    null
                } else {
                    pageIndex + (params.loadSize / NETWORK_PAGE_SIZE)
                }
            Log.d("Kane", "load: nextKey = $nextKey")
            LoadResult.Page(
                data    = arrayData,
                prevKey = if (pageIndex == STARTING_PAGE_INDEX) null else pageIndex,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}