package com.example.littlelivesassignment.data.local.converter

import androidx.room.TypeConverter
import com.example.littlelivesassignment.data.model.EventSnapshot
import com.google.gson.Gson

class UserEventTypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromEventSnapshot(eventSnapshot: EventSnapshot?): String? {
        return eventSnapshot?.let {
            gson.toJson(eventSnapshot)
        }
    }

    @TypeConverter
    fun toEventSnapshot(eventSnapshotJson: String?): EventSnapshot? {
        return eventSnapshotJson?.let {
            gson.fromJson(eventSnapshotJson, EventSnapshot::class.java)
        }
    }
}
