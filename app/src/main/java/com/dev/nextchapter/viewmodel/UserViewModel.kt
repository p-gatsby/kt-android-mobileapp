package com.dev.nextchapter.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import com.dev.nextchapter.data.User
import com.dev.nextchapter.data.UserDatabase
import kotlinx.coroutines.Dispatchers

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao = UserDatabase.getDatabase(application).userDao()

    // Function to handle user login
    fun login(username: String, password: String) = liveData(Dispatchers.IO) {
        val user = userDao.login(username, password)
        emit(user)
    }

    fun signUp(username: String, password: String) = liveData(Dispatchers.IO) {
        val existingUser = userDao.getUserByUsername(username)
        if (existingUser == null) {
            val newUser = User(username = username, password = password)
            userDao.insert(newUser)
            emit(true)
        } else {
            emit(false)
        }
    }
}