package com.dev.nextchapter.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.dev.nextchapter.viewmodel.BookViewModel
import com.dev.nextchapter.viewmodel.UserViewModel

@Composable
fun BookDetailScreen(
    volumeId: String,
    userViewModel: UserViewModel = viewModel(),
    bookViewModel: BookViewModel = viewModel(),
    navController: NavController
) {

    LaunchedEffect(key1 = volumeId) {
        bookViewModel.getBook(volumeId)
    }

    val bookState by bookViewModel.bookState
    val currentUser by userViewModel.currentUser.observeAsState()
//
    val imageModifier = Modifier
        .border(BorderStroke(5.dp , Color.hsl(0.7f, 0.77f, 0.38f)))
        .background(Color.hsl(0.19f, 0.57f, 0.33f))

    Image(
        painter = painterResource(id = R.drawable.bookonpedestal),
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
            bookState.loading -> {
                CircularProgressIndicator()
            }

            bookState.error != null -> {
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
                Text(text = bookState.error ?: "Unknown Error")
            }

            bookState.book != null -> {
                val book = bookState.book!!

                val imageUrl = book.volumeInfo.imageLinks?.thumbnail ?: ""
                // Create a custom ImageLoader for debugging
                val imageLoader = ImageLoader.Builder(LocalContext.current)
                    .logger(DebugLogger())
                    .error(android.R.drawable.ic_menu_report_image) // Fallback image if error
                    .build()

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
                            .size(250.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = rememberAsyncImagePainter(android.R.drawable.ic_menu_report_image),
                        contentDescription = "Fallback Image",
                        modifier = Modifier
                            .size(150.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )

                }

                Text(text = book.volumeInfo.title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = "By: ${book.volumeInfo.authors.joinToString()}",
                    fontSize = 20.sp,
                    color = Color.DarkGray
                )
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    if (currentUser?.readBooks?.contains(volumeId) == true) {
                        Button(onClick = {
                            userViewModel.removeBookToRead(volumeId)
                        }) { Text("Remove from Have Read") }
                    } else {
                        Button(onClick = {
                            userViewModel.addBookToRead(volumeId)
                        }) { Text("Add to Have Read") }
                    }

                    if (currentUser?.wantToReadList?.contains(volumeId) == true) {
                        Button(onClick = {
                            userViewModel.removeBookToWantToReadList(volumeId)
                        }) { Text("Remove from Have Read") }
                    } else {
                        Button(onClick = {
                            userViewModel.addBookToWantToReadList(volumeId)
                        }) { Text("Want to Read") }
                    }

                }
                Text(
                    text = stripHTMLTags(book.volumeInfo.description), color = Color.White,
                    modifier = Modifier

                        .verticalScroll(
                            rememberScrollState()
                        )
                        .paint(painter = painterResource(id = R.drawable.bookdetailsonmagicbook), contentScale = ContentScale.FillHeight)
                        .padding(16.dp),
                    textAlign = TextAlign.Justify
                )
            }
        }
    }
}

fun stripHTMLTags(htmlText: String): String {
    return htmlText.replace(Regex("<[^>]*>"), "")
}