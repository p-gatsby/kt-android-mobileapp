package com.dev.nextchapter.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import coil.transform.RoundedCornersTransformation
import coil.util.DebugLogger
import com.dev.nextchapter.R
import com.dev.nextchapter.data.Book
import com.dev.nextchapter.viewmodel.BookViewModel

@Composable
fun SearchScreen(
    query: String,
    bookViewModel: BookViewModel = viewModel(),
    navController: NavController
) {
    LaunchedEffect(key1 = query) {
        bookViewModel.searchBooks(query)
    }

    val searchState by bookViewModel.searchListState
//
    val imageModifier = Modifier
        .border(BorderStroke(1.dp , Color.hsl(0.7f, 0.77f, 0.38f)))
        .background(Color.hsl(0.19f, 0.57f, 0.33f))

    Image(
        painter = painterResource(id = R.drawable.libraryhallway),
        contentDescription = stringResource(id = R.string.app_name),
        contentScale = ContentScale.Crop,
        modifier = imageModifier.fillMaxSize())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Transparent)
            .padding(top = 42.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            searchState.loading -> {
                Text("Search Results for \"$query\"", fontSize = 24.sp,)
                CircularProgressIndicator()
            }

            searchState.error != null -> {
                Button(
                    onClick = {
                        navController.navigate("home")
                    }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Go Back"
                    )
                    Text("Go Back", color = Color.White)
                }
                Text(text = searchState.error ?: "Unknown Error")
            }

            searchState.bookList.isNotEmpty() -> {
                Row(
                    modifier = Modifier
                        .background(color = Color.Transparent, shape = RoundedCornerShape(25.dp)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        "Search results for \"$query\"",
                        fontSize = 20.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Button(
                        onClick = {
                            navController.navigate("home")
                        }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go Back"
                        )
                        Text("Go Back")
                    }
                }
                LazyVerticalGrid(modifier = Modifier
                    .background(color = Color.Transparent,shape = RoundedCornerShape(10.dp)

                        ),
                    columns = GridCells.Fixed(1), // Display two categories per row
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(searchState.bookList) {
                        BookItem(it, navController)
                    }

                }
            }
        }
    }
}

@Composable
fun BookItem(book: Book, navController: NavController) {
    val imageUrl = book.volumeInfo.imageLinks?.thumbnail ?: ""

    // Create a custom ImageLoader for debugging
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .logger(DebugLogger())
        .error(android.R.drawable.ic_menu_report_image) // Fallback image if error
        .build()


    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                navController.navigate("bookdetails/${book.id}")
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = book.volumeInfo.title, maxLines = 1, overflow = TextOverflow.Ellipsis)
        if (imageUrl.isNotEmpty()) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .size(Size.ORIGINAL) // Fetch the original size
                        .crossfade(true)
                        .transformations(RoundedCornersTransformation(8f))
                        .build(),
                    imageLoader = imageLoader
                ),
                contentDescription = book.volumeInfo.title,
                modifier = Modifier
                    .size(200.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                painter = rememberAsyncImagePainter(android.R.drawable.ic_menu_report_image),
                contentDescription = "Fallback Image",
                modifier = Modifier
                    .size(200.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        }


    }
}