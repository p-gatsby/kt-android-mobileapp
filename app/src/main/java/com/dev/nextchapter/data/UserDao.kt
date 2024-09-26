package com.dev.nextchapter.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    // Insert a new user
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    // Check if a user exists with a given username and password
    // (for login)
    @Query("SELECT * FROM user_table WHERE username = :username " +
            "AND password = :password LIMIT 1")
    suspend fun login(username: String, password: String) : User?

    // Check if a username is already taken
    // (for signup)
    @Query("SELECT * FROM user_table WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): User?
}