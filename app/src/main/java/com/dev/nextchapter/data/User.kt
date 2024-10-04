package com.dev.nextchapter.data

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val username: String,
    val password: String,
    val salt: String,
    val favoriteGenres: MutableList<String> = mutableListOf(),
    val readBooks: MutableList<String> = mutableListOf(),
    val wantToReadList: MutableList<String> = mutableListOf(),
) : Parcelable
