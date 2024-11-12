package com.dev.nextchapter.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromMutableStringList(list: MutableList<String>?): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toMutableStringList(value: String?): MutableList<String> {
        val listType = object : TypeToken<MutableList<String>>() {}.type

        return value?.let {
            gson.fromJson(it, listType)
        } ?: mutableListOf()
    }

    @TypeConverter
    fun fromMutableIntList(list: MutableList<Int>?): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toMutableIntList(value: String?): MutableList<Int> {
        val listType = object : TypeToken<MutableList<Int>>() {}.type

        return value?.let {
            gson.fromJson(it, listType)
        } ?: mutableListOf()
    }

    @TypeConverter
    fun fromBookList(bookList: BookList?): String {
        return gson.toJson(bookList)
    }

    @TypeConverter
    fun toBookList(value: String?): BookList {
        val listType = object : TypeToken<BookList>() {}.type
        return value?.let {
            gson.fromJson(it, listType)
        } ?: BookList(0, 0, false, "", mutableListOf(), mutableListOf())
    }


    @TypeConverter
    fun fromBookLists(list: MutableList<BookList>?): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toBookLists(value: String?): MutableList<BookList> {
        val listType = object : TypeToken<MutableList<BookList>>() {}.type
        return value?.let {
            gson.fromJson(it, listType)
        } ?: mutableListOf()
    }
}