package com.example.littlelivesassignment.data.local.deserializer

import com.example.littlelivesassignment.data.model.*
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class EventSnapshotDeserializer : JsonDeserializer<EventSnapshot> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): EventSnapshot? {
        if (json == null) return null

        val jsonObject = json.asJsonObject
        val eventType = jsonObject.get("eventType")?.asString

        return when (eventType) {
            "event" -> {
                context?.deserialize<EventSnapshot>(
                    jsonObject.get("eventSnapshot"),
                    object : TypeToken<ChildEvent>() {}.type
                )
            }
            "checkIn", "checkOut" -> {
                context?.deserialize<EventSnapshot>(
                    jsonObject.get("eventSnapshot"),
                    object : TypeToken<AttendanceTrackingEvent>() {}.type
                )
            }
            "everydayHealth" -> {
                context?.deserialize<EventSnapshot>(
                    jsonObject.get("eventSnapshot"),
                    object : TypeToken<ActivityEvent>() {}.type
                )
            }
            "portfolio" -> {
                context?.deserialize<EventSnapshot>(
                    jsonObject.get("eventSnapshot"),
                    object : TypeToken<PortfolioEvent>() {}.type
                )
            }
            "story_exported" -> {
                context?.deserialize<EventSnapshot>(
                    jsonObject.get("eventSnapshot"),
                    object : TypeToken<StoryExportedEvent>() {}.type
                )
            }
            "story_published" -> {
                context?.deserialize<EventSnapshot>(
                    jsonObject.get("eventSnapshot"),
                    object : TypeToken<StoryPublishedEvent>() {}.type
                )
            }
            // add more cases for other event types
            else -> null
        }
    }
}