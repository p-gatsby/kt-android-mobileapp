package com.dev.nextchapter.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.dev.nextchapter.data.User
import com.dev.nextchapter.data.UserDatabase
import com.dev.nextchapter.utils.HashUtils
import kotlinx.coroutines.Dispatchers

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao = UserDatabase.getDatabase(application).userDao()

    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> get() = _currentUser

    // Function to set the current logged-in user
    fun setCurrentUser(user: User) {
        _currentUser.value = user
    }

    // Function to handle user login
    fun login(username: String, password: String) = liveData(Dispatchers.IO) {
        val user = userDao.getUserByUsername(username)
        if (user != null) {
            val hashedPassword = HashUtils.hashPassword(password, user.salt)
            // Verify the user if the hashed passwords are equal
            if (hashedPassword == user.password) {
                emit(user)
            } else {
                emit(null)
            }
        } else {
            emit(null)
        }
    }

    fun signUp(username: String, password: String) = liveData(Dispatchers.IO) {
        val existingUser = userDao.getUserByUsername(username)
        if (existingUser == null) {
            val salt = HashUtils.generateSalt() // Generate the salt
            val hashedPassword =
                HashUtils.hashPassword(password, salt) // Hash the password with salt
            val newUser = User(username = username, password = hashedPassword, salt = salt)
            userDao.insert(newUser)
            emit(true)
        } else {
            emit(false)
        }
    }


    // Function to logout the user
    fun logoutUser() {
        _currentUser.value = null
    }
}