package com.dev.nextchapter.utils


import java.security.MessageDigest
import java.security.SecureRandom
import kotlin.random.Random

object HashUtils {

    // Generate a random salt
    fun generateSalt(): String {
        val salt = ByteArray(16)
        SecureRandom().nextBytes(salt)
        return salt.joinToString("") {
            "%02x".format(it)
        }
    }

    // Hash the password using SHA-256 and the salt
    fun hashPassword(password: String, salt: String): String {
        val bytes = MessageDigest
            .getInstance("SHA-256")
            .digest((password + salt).toByteArray())
        return bytes.joinToString("") {
            "%02x".format(it)
        }
    }
}