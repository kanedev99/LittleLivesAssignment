package com.example.littlelivesassignment.data.model

import java.lang.Exception

sealed class UserStoryResult {
    data class Success(val data: List<Event>) : UserStoryResult()
    data class Error(val error: Exception) : UserStoryResult()
}
