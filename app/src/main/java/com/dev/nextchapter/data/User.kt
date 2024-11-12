package com.dev.nextchapter.data

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val username: String,
    val password: String,
    val salt: String,

    @TypeConverters(Converters::class)
    val favoriteGenres: MutableList<String> = mutableListOf(),

    @TypeConverters(Converters::class)
    val haveRead: BookList = BookList(
        authorId = id,
        allowCollaborators = false,
        title = "Books I've Read",
        bookList = mutableListOf(),
        collaborators = mutableListOf()
    ),

    @TypeConverters(Converters::class)
    val wantToRead: BookList = BookList(
        authorId = id,
        allowCollaborators = false,
        title = "Want To Read",
        bookList = mutableListOf(),
        collaborators = mutableListOf()
    ),

    @TypeConverters(Converters::class)
    val bookLists: MutableList<BookList> = mutableListOf()


) : Parcelable
