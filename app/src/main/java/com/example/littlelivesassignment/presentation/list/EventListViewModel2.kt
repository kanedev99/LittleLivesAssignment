package com.example.littlelivesassignment.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.littlelivesassignment.data.model.EventType
import com.example.littlelivesassignment.data.model.UserEvent
import com.example.littlelivesassignment.data.remote.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventListViewModel2 @Inject constructor(private val apiService: ApiService): ViewModel() {
    private val _events = MutableLiveData<List<UserEvent>>()
    val events: LiveData<List<UserEvent>>
        get() = _events

    init {
        viewModelScope.launch {
            _events.postValue(
                apiService.getData().body()?.data?.userTimeline
                    ?.filterNot { it.type == EventType.ACTIVITY || it.type == EventType.PORTFOLIO} ?: listOf())
        }
    }
}