package com.dev.nextchapter.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.nextchapter.data.Book
import com.dev.nextchapter.data.googleService
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val _booksState = mutableStateOf(BookState())
    val bookState: State<BookState> = _booksState

    fun searchBooks(query: String) {

        viewModelScope.launch {
            try {
                val response = googleService.searchBooks(query = query)
                _booksState.value = _booksState.value.copy(
                    loading = false,
                    bookList = response.items,
                    error = null
                )

            } catch (e: Exception) {
                _booksState.value = _booksState.value.copy(
                    loading = false,
                    error = "Error fetching books ${e.message}"
                )
            }
        }
    }

    data class BookState(
        val loading: Boolean = true,
        val bookList: List<Book> = emptyList(),
        val error: String? = null
    )
}