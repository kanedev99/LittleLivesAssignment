package com.example.littlelivesassignment.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.littlelivesassignment.data.repository.UserStoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(private val userStoryRepository: UserStoryRepository): ViewModel() {

//    val events = userStoryRepository.getEventsResult().cachedIn(viewModelScope)

}