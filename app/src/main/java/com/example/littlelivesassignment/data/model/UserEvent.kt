package com.example.littlelivesassignment.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "event_list")
data class UserEvent(
    @PrimaryKey(autoGenerate = true)                    val id          : Int = 0,
    @field:SerializedName("__typename")                 val typeName    : String?,
    @field:SerializedName("eventDate")                  val date        : String?,
    @field:SerializedName("eventDescription")           val description : String?,
    @field:SerializedName("eventSnapshot")              val snapshot    : Any?,
    @field:SerializedName("eventType")                  val type        : String?,
                                                        val insertAt    : String?
)