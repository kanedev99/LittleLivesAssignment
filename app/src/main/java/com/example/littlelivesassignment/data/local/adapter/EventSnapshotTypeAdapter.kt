package com.example.littlelivesassignment.data.local.adapter

import com.example.littlelivesassignment.data.model.EventSnapshot
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

class EventSnapshotTypeAdapter : TypeAdapter<EventSnapshot>() {
    override fun write(out: JsonWriter, value: EventSnapshot) {
        // implement serialization
        out.jsonValue(Gson().toJson(value))
    }

    override fun read(`in`: JsonReader): EventSnapshot {
        // implement deserialization
        return Gson().fromJson(`in`.nextString(), EventSnapshot::class.java)
    }
}
