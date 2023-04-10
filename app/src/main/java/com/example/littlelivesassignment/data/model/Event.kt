package com.example.littlelivesassignment.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "event_list")
data class Event(
    @PrimaryKey(autoGenerate = true)            val id          : Int = 0,
    @field:SerializedName("__typename")         val typeName    : String?,
    @field:SerializedName("eventDate")          val date        : String?,
    @field:SerializedName("eventDescription")   val description : String?,
    @field:SerializedName("eventSnapshot")      val snapshot    : String?,
    @field:SerializedName("eventType")          val type        : String?,
    @field:SerializedName("insertedAt")         val insertAt    : String?
)