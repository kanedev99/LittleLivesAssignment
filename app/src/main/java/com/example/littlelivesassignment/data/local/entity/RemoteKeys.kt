package com.example.littlelivesassignment.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey val eventId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)
