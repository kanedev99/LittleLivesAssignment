package com.example.littlelivesassignment.data.model

import com.google.gson.annotations.SerializedName

data class UserEvent(
                                                val id          : Int? = 0,
    @field:SerializedName("__typename")         val typeName    : String?,
    @field:SerializedName("eventDate")          val date        : String?,
    @field:SerializedName("eventDescription")   val description : String?,
    @field:SerializedName("eventSnapshot")      val snapshot    : Any?,
    @field:SerializedName("eventType")          val type        : String?,
                                                val insertAt    : String?
)