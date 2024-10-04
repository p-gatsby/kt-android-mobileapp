package com.dev.nextchapter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dev.nextchapter.ui.screen.BookDetailScreen
import com.dev.nextchapter.ui.screen.HomeScreen
import com.dev.nextchapter.ui.screen.LoginScreen
import com.dev.nextchapter.ui.screen.SearchScreen
import com.dev.nextchapter.ui.screen.SignupScreen
import com.dev.nextchapter.ui.screen.UserProfileScreen
import com.dev.nextchapter.ui.theme.NextChapterTheme
import com.dev.nextchapter.viewmodel.UserViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            enableEdgeToEdge()
            val userViewModel: UserViewModel by viewModels()

            NextChapterTheme {
                val navController = rememberNavController()
                NavHost(navController,
                    startDestination = "login",
                    enterTransition = { EnterTransition.None },
                    exitTransition = { ExitTransition.None }) {
                    composable("login") {
                        LoginScreen(navController, userViewModel)
                    }
                    composable("signup") {
                        SignupScreen(navController)
                    }
                    composable("home") {
                        HomeScreen(navController, userViewModel)
                    }
                    composable("profile"){
                        UserProfileScreen(userViewModel, navController)
                    }
                    composable("searchbooks/{query}") { backStackEntry ->
                        val query = backStackEntry.arguments?.getString("query")
                        query?.let {
                            SearchScreen(query, navController = navController)
                        }
                    }
                    composable("bookdetails/{volumeId}") { backStackEntry ->
                        val volumeId = backStackEntry.arguments?.getString("volumeId")
                        volumeId?.let {
                            BookDetailScreen(
                                volumeId,
                                userViewModel = userViewModel,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}
