package com.example.littlelivesassignment.data.source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.littlelivesassignment.data.model.UserEvent
import com.example.littlelivesassignment.data.remote.ApiService
import com.example.littlelivesassignment.data.repository.UserEventRepository
import com.example.littlelivesassignment.data.repository.UserEventRepository.Companion.NETWORK_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserEventDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : UserEventDataSource {


    override fun getUserEvents(): Flow<PagingData<UserEvent>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                UserEventRepository(apiService)
            }
        ).flow
    }

}