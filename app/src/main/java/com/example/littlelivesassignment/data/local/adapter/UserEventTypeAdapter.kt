package com.example.littlelivesassignment.data.local.adapter

import com.example.littlelivesassignment.data.model.ChildEvent
import com.example.littlelivesassignment.data.model.UserEvent
import com.example.littlelivesassignment.utils.ext.readObject
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

/*class UserEventTypeAdapter: TypeAdapter<UserEvent>() {
    override fun write(out: JsonWriter?, value: UserEvent?) {
        // Do nothing
    }

    override fun read(`in`: JsonReader): UserEvent {
        var __typename: String? = null
        var eventDate: String? = null
        var eventDescription: String? = null
        var eventSnapshot: String? = null
        var eventType: String? = null
        var insertedAt: String? = null

        `in`.readObject { name ->
            when (name) {
                "__typename"        -> __typename = `in`.nextString()
                "eventDate"         -> eventDate = `in`.nextString()
                "eventDescription"  -> eventDescription = `in`.nextString()
                "eventSnapshot"     -> {

                }
                "eventType"         -> eventType = `in`.nextString()
                "insertedAt"        -> insertedAt = `in`.nextString()
                else -> `in`.skipValue()
            }
        }
        return UserEvent(
            typeName = __typename,
            date = eventDate,
            description = eventDescription,
            snapshot = eventSnapshot,
            type = eventType,
            insertAt = insertedAt
        )
    }

    private fun readChildEvent(`in`: JsonReader): ChildEvent {
        var childId = ""
        var childName = ""
        var eventDate = ""
        var eventTitle = ""

        `in`.apply {
            readObject { name ->
                when(name) {
                    "childId" -> childId = nextString()
                    "childName" -> childName = nextString()
                    "eventDate" -> eventDate = nextString()
                    "eventTitle" -> eventTitle = nextString()
                    else -> skipValue()
                }
            }
        }
        return ChildEvent(childId, childName, eventDate, eventTitle)
    }

}*/
