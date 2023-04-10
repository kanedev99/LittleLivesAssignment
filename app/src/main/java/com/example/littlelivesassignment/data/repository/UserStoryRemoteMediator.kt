package com.example.littlelivesassignment.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.littlelivesassignment.data.local.db.EventDatabase
import com.example.littlelivesassignment.data.local.entity.RemoteKeys
import com.example.littlelivesassignment.data.model.Event
import com.example.littlelivesassignment.data.remote.ApiService
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class UserStoryRemoteMediator(
    private val service: ApiService,
    private val eventDatabase: EventDatabase
) : RemoteMediator<Int, Event>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Event>): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val apiResponse = service.getData(page, state.config.pageSize)

            val events = apiResponse.data.userTimeline
            val endOfPaginationReached = events.isEmpty()
            eventDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    eventDatabase.remoteKeysDao().clearRemoteKeys()
                    eventDatabase.eventDao().clearEvents()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = events.map {
                    RemoteKeys(eventId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                eventDatabase.remoteKeysDao().insertAll(keys)
                eventDatabase.eventDao().insertAll(events)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Event>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { event -> eventDatabase.remoteKeysDao().remoteKeysEventId(event.id) }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Event>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { event -> eventDatabase.remoteKeysDao().remoteKeysEventId(event.id) }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Event>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { eventId ->
                eventDatabase.remoteKeysDao().remoteKeysEventId(eventId)
            }
        }
    }
}