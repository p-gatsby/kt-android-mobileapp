package com.dev.nextchapter.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.dev.nextchapter.data.BookCategory
import com.dev.nextchapter.data.bookCategories
import com.dev.nextchapter.viewmodel.UserViewModel

@Composable
fun HomeScreen(navController: NavController, userViewModel: UserViewModel = viewModel()) {
    val currentUser = userViewModel.currentUser
    var showMenu by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE1DCC5))
    ) {
        Column(
            modifier = Modifier
                .padding(top = 36.dp, start = 16.dp, end = 16.dp)
                .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(modifier = Modifier
                .padding(8.dp)
                .clickable { showMenu = true }
                .align(Alignment.End)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    currentUser.value?.let { user ->
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "user-icon"
                        )
                        Text(text = user.username)
                    }

                    DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                        DropdownMenuItem(
                            text = { Text("Log out") },
                            onClick = {
                                showMenu = false
                                userViewModel.logoutUser()
                                navController.navigate("login")
                            }
                        )
                    }
                }
            }

            Text(
                "Select a Genre",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )

            BookCategoryGrid(bookCategories)

        }
    }

}

@Composable
fun BookCategoryGrid(bookCategories: List<BookCategory>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // Display two categories per row
        contentPadding = PaddingValues(16.dp)
    ) {
        items(bookCategories) { category ->
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = rememberAsyncImagePainter(category.coverUrl),
                    contentDescription = category.name,
                    modifier = Modifier
                        .size(200.dp) // Same height and width
                        .clip(RoundedCornerShape(8.dp))
                        .border(2.dp, Color.Gray, RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = category.name,
                )
            }
        }
    }
}
