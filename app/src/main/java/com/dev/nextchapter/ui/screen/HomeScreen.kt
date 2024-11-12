package com.dev.nextchapter.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.dev.nextchapter.R
import com.dev.nextchapter.data.BookCategory
import com.dev.nextchapter.data.bookCategories
import com.dev.nextchapter.viewmodel.UserViewModel

@Composable
fun HomeScreen(navController: NavController, userViewModel: UserViewModel = viewModel()) {
    val currentUser = userViewModel.currentUser
    var showMenu by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
//    val linear = Brush.linearGradient(listOf(
//        Color.hsl(300f, 0.26f, 0.71f),
//        Color.hsl(299f, 0.9f, 0.1f,),
//        Color.hsl(300f, 0.26f, 0.71f),
//        Color.hsl(299f, 0.9f, 0.1f,)
//    ))
    val imageModifier = Modifier
        .border(BorderStroke(5.dp , Color.hsl(0.7f, 0.77f, 0.38f)))
        .background(Color.hsl(0.19f, 0.57f, 0.33f))

    Image(
        painter = painterResource(id = R.drawable.libraryhallway),
        contentDescription = stringResource(id = R.string.app_name),
        contentScale = ContentScale.Crop,
        modifier = imageModifier.fillMaxSize())



    Column(
        modifier = Modifier
            //.background(linear)
            .padding(top = 36.dp, start = 16.dp, end = 16.dp)
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Row(
            modifier = Modifier
                .padding(16.dp)
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween

        ) {

            // Improved Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search Books") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 14.sp),
                modifier = Modifier
                    .height(56.dp)
                    .weight(1f)
                    .padding(end = 8.dp),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                    if (searchQuery.isNotEmpty()) {
                        navController.navigate("searchbooks/${searchQuery}")
                    }
                })
            )

            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { showMenu = true },
                verticalAlignment = Alignment.Bottom
            ) {
                currentUser.value?.let { user ->
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = Icons.Default.Person,
                        tint = Color.DarkGray,
                        contentDescription = "user-icon"
                    )
                    Text(
                        text = user.username,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
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
                    DropdownMenuItem(
                        text = { Text("My Account") },
                        onClick = {
                            showMenu = false
                            navController.navigate("profile")
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

@Composable
fun BookCategoryGrid(bookCategories: List<BookCategory>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1), // Display one book in line
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
                        .size(150.dp) // Same height and width
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

