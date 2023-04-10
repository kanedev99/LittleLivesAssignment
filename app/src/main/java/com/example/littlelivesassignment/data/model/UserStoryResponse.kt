package com.example.littlelivesassignment.data.model

import com.google.gson.annotations.SerializedName

data class UserStoryResponse(
    @field:SerializedName("data") val data: UserTimeline
)