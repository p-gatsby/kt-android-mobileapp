package com.dev.nextchapter.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import coil.transform.RoundedCornersTransformation
import coil.util.DebugLogger
import com.dev.nextchapter.data.Book
import com.dev.nextchapter.viewmodel.SearchViewModel

@Composable
fun SearchScreen(query: String, searchViewModel: SearchViewModel = SearchViewModel()) {
    LaunchedEffect(key1 = query) {
        searchViewModel.searchBooks(query)
    }

    val searchState by searchViewModel.bookState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE1DCC5))
            .padding(top = 42.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            searchState.loading -> {
                Text("Search Results for \"$query\"", fontSize = 24.sp)
                CircularProgressIndicator()
            }

            searchState.error != null -> {
                Text(text = searchState.error ?: "Unknown Error")
            }

            searchState.bookList.isNotEmpty() -> {
                Row {
                    Text("Search Results for \"$query\"", fontSize = 14.sp)
                    Button(onClick = {}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go Back"
                        )
                        Text("Go Back")
                    }
                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), // Display two categories per row
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(searchState.bookList) {
                        BookItem(it)

                    }

                }
            }
        }
    }
}

@Composable
fun BookItem(book: Book) {
    val imageUrl = book.volumeInfo.imageLinks?.thumbnail

    // Create a custom ImageLoader for debugging
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .logger(DebugLogger())
        .error(android.R.drawable.ic_menu_report_image) // Fallback image if error
        .build()


    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = book.volumeInfo.title, maxLines = 1, overflow = TextOverflow.Ellipsis)
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

    }
}