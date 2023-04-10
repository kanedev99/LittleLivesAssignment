package com.example.littlelivesassignment.data.model

import com.google.gson.annotations.SerializedName

data class UserTimeline(
    @field:SerializedName("userTimeline") val userTimeline: List<Event>
)