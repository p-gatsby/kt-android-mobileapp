package com.dev.nextchapter.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromStringList(list: List<String>?): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toStringList(value: String?): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type

        return value?.let {
            gson.fromJson(it, listType)
        } ?: emptyList()
    }
}