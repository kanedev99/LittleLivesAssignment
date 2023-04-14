package com.example.littlelivesassignment.data.local.converter

import androidx.room.TypeConverter
import com.example.littlelivesassignment.data.model.*
import com.google.gson.Gson
import org.json.JSONObject

class EventSnapshotTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromChildEvent(value: ChildEvent): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toChildEvent(value: String): ChildEvent {
        return gson.fromJson(value, ChildEvent::class.java)
    }

    @TypeConverter
    fun fromActivityEvent(value: ActivityEvent): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toActivityEvent(value: String): ActivityEvent {
        return gson.fromJson(value, ActivityEvent::class.java)
    }

}
