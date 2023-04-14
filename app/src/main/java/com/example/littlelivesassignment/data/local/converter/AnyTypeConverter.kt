package com.example.littlelivesassignment.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AnyTypeConverter {

    @TypeConverter
    fun fromAny(any: Any?): String? {
        if (any == null) {
            return null
        }
        val gson = Gson()
        return gson.toJson(any)
    }

    @TypeConverter
    fun toAny(anyString: String?): Any? {
        if (anyString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<Any>() {}.type
        return gson.fromJson(anyString, type)
    }
}