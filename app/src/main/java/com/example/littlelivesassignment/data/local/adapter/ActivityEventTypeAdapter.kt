package com.example.littlelivesassignment.data.local.adapter

import com.example.littlelivesassignment.data.model.ActivityEvent
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

class ActivityEventTypeAdapter : TypeAdapter<ActivityEvent>() {
    override fun write(out: JsonWriter, value: ActivityEvent) {
        // implement serialization
        out.jsonValue(Gson().toJson(value))
    }

    override fun read(`in`: JsonReader): ActivityEvent {
        // implement deserialization
        return Gson().fromJson(`in`.nextString(), ActivityEvent::class.java)
    }
}