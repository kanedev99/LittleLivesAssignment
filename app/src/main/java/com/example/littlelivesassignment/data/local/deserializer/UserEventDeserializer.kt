package com.example.littlelivesassignment.data.local.deserializer

import android.util.Log
import com.example.littlelivesassignment.data.model.*
import com.example.littlelivesassignment.data.model.EventType.ACTIVITY
import com.example.littlelivesassignment.data.model.EventType.CHECK_IN
import com.example.littlelivesassignment.data.model.EventType.CHECK_OUT
import com.example.littlelivesassignment.data.model.EventType.CREATE
import com.example.littlelivesassignment.data.model.EventType.EXPORTED_STORY
import com.example.littlelivesassignment.data.model.EventType.PORTFOLIO
import com.example.littlelivesassignment.data.model.EventType.PUBLISHED_STORY
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class UserEventDeserializer : JsonDeserializer<UserEvent> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): UserEvent? {
        if (json == null) return null

        val jsonObject = json.asJsonObject
        val eventType = jsonObject.get("eventType")?.asString
        val eventSnapshotString = jsonObject.get("eventSnapshot")?.asString

        val gson = Gson()

        val eventSnapshot = when (eventType) {
            CREATE              -> gson.fromJson(eventSnapshotString, ChildEvent::class.java)
            ACTIVITY            -> gson.fromJson(eventSnapshotString, ActivityEvent::class.java)
            PORTFOLIO           -> gson.fromJson(eventSnapshotString, PortfolioEvent::class.java)
            EXPORTED_STORY      -> gson.fromJson(eventSnapshotString, StoryExportedEvent::class.java)
            PUBLISHED_STORY     -> gson.fromJson(eventSnapshotString, StoryPublishedEvent::class.java)
            CHECK_IN, CHECK_OUT -> gson.fromJson(eventSnapshotString, AttendanceTrackingEvent::class.java)
            else -> null
        }

        Log.d("Kane", "deserialize: type = $eventType")

        return UserEvent(
            typeName    = jsonObject.get("__typename")?.asString,
            date        = jsonObject.get("eventDate")?.asString,
            description = jsonObject.get("eventDescription")?.asString,
            snapshot    = eventSnapshot,
            type        = eventType,
            insertAt    = jsonObject.get("insertedAt")?.asString
        )
    }
}