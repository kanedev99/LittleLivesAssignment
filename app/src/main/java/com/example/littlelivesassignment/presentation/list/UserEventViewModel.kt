package com.example.littlelivesassignment.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.littlelivesassignment.data.model.UserEvent
import com.example.littlelivesassignment.data.source.UserEventDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class UserEventViewModel @Inject constructor(
    private val userEventDataSource: UserEventDataSource
) : ViewModel() {

    fun getUserEvents(): Flow<PagingData<UserEvent>> {
        return userEventDataSource.getUserEvents().cachedIn(viewModelScope)
    }

}