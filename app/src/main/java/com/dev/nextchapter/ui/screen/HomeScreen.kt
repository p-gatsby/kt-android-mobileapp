package com.dev.nextchapter.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dev.nextchapter.viewmodel.UserViewModel

@Composable
fun HomeScreen(userViewModel: UserViewModel = viewModel()) {
    val currentUser = userViewModel.currentUser

    Column(modifier = Modifier.padding(32.dp)) {
        currentUser.value?.let { user ->
            Text(text = "Welcome, ${user.username}")
        }
    }
}