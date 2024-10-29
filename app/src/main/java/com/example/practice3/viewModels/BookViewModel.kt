package com.example.practice3.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practice3.data.BooksRepository
import com.example.practice3.data.IBooksRepository
import com.example.practice3.models.Book
import com.example.practice3.state.ListState
import com.example.practice3.utils.launchLoadingAndError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class BookViewModel(val repository: IBooksRepository): ViewModel() {
    private val mutableState = MutableListState()
    val viewState = mutableState as ListState

    init {
        loadFilms()
    }

    private fun loadFilms() {
        viewModelScope.launchLoadingAndError(
            handleError = { mutableState.error = it.localizedMessage },
            updateLoading = { mutableState.loading = it }
        ) {
            mutableState.error = null

            mutableState.items = repository.getBooks(viewState.searchName)
        }
    }

    private class MutableListState : ListState {
        override var searchName: String by mutableStateOf("harry")
        override var items: List<Book> by mutableStateOf(emptyList())
        override var error: String? by mutableStateOf(null)
        override var loading: Boolean by mutableStateOf(false)
    }
}