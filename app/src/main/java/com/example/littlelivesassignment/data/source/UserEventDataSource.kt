package com.example.littlelivesassignment.data.source

import androidx.paging.PagingData
import com.example.littlelivesassignment.data.model.UserEvent
import kotlinx.coroutines.flow.Flow

interface UserEventDataSource {

    fun getUserEvents(): Flow<PagingData<UserEvent>>
}