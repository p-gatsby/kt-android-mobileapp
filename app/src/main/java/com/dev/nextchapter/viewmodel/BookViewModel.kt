package com.dev.nextchapter.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.nextchapter.data.Book
import com.dev.nextchapter.data.BookList
import com.dev.nextchapter.data.googleService
import kotlinx.coroutines.launch

class BookViewModel : ViewModel() {

    private val _searchListState = mutableStateOf(SearchState())
    val searchListState: State<SearchState> = _searchListState

    private val _bookDetailState = mutableStateOf(BookDetailState())
    val bookDetailState: State<BookDetailState> = _bookDetailState

    fun searchBooks(query: String) {
        viewModelScope.launch {
            try {
                val response = googleService.searchBooks(query = query)
                _searchListState.value = _searchListState.value.copy(
                    loading = false,
                    bookList = response.bookList,
                )

            } catch (e: Exception) {
                _searchListState.value = _searchListState.value.copy(
                    loading = false,
                    error = "Error fetching books ${e.message}"
                )
            }
        }
    }

    fun getBook(volumeId: String) {
        viewModelScope.launch {
            try {
                val response = googleService.getBookDetails(volumeId = volumeId)
                _bookDetailState.value = _bookDetailState.value.copy(
                    loading = false,
                    book = response,
                )
            } catch (e: Exception) {
                _bookDetailState.value = _bookDetailState.value.copy(
                    loading = false,
                    error = "Error fetching volumeId($volumeId):  ${e.message}"
                )
            }
        }
    }

    data class SearchState(
        val loading: Boolean = true,
        val bookList: List<Book> = emptyList(),
        val error: String? = null
    )

    data class BookDetailState(
        val loading: Boolean = true,
        val book: Book? = null,
        val error: String? = null,
    )
}