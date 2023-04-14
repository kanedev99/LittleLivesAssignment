package com.example.littlelivesassignment.utils.ext

import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken

inline fun JsonReader.readObject(caseAction: (String) -> Unit) {
    beginObject()
    var name: String
    while (hasNext()) {
        name = nextName()
        if (peek() == JsonToken.NULL) {
            nextNull()
            continue
        }
        caseAction(name)
    }
    endObject()
}

inline fun JsonReader.readArray(action: () -> Unit) {
    beginArray()
    while (hasNext()) {
        action()
    }
    endArray()
}
