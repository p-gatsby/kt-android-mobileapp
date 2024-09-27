package com.dev.nextchapter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dev.nextchapter.ui.screen.HomeScreen
import com.dev.nextchapter.ui.screen.LoginScreen
import com.dev.nextchapter.ui.screen.SignupScreen
import com.dev.nextchapter.ui.theme.NextChapterTheme
import com.dev.nextchapter.viewmodel.UserViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            enableEdgeToEdge()
            val userView: UserViewModel by viewModels()

            NextChapterTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "login") {
                    composable("login") {
                        LoginScreen(navController, userView)
                    }
                    composable("signup") {
                        SignupScreen(navController)
                    }
                    composable("home") {
                        HomeScreen(userView)
                    }
                }
            }
        }
    }
}
