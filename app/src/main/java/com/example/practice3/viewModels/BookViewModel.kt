package com.example.practice3.viewModels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practice3.data.DataPreferences
import com.example.practice3.data.IBooksRepository
import com.example.practice3.data.db.FavouriteBookEntity
import com.example.practice3.models.Book
import com.example.practice3.state.ListState
import com.example.practice3.utils.launchLoadingAndError
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class BookViewModel(
    val repository: IBooksRepository,
    val context: Context
): ViewModel() {
    private val mutableState = MutableListState()
    val viewState = mutableState as ListState

    private var mutableFavouriteBookState = mutableStateOf<List<Book>>(emptyList())
    val favoriteGames = mutableFavouriteBookState as State<List<Book>>

    init {
        initState()
        loadBooks()
        loadFavoriteBooks()
    }

    private fun initState(){
        viewModelScope.launch {
            mutableState.searchName = loadFilters()
        }
    }

    private suspend fun loadFilters(): String {
        return DataPreferences.getSearchName(context).first()
    }

    private fun loadBooks() {
        viewModelScope.launchLoadingAndError(
            handleError = { mutableState.error = it.localizedMessage },
            updateLoading = { mutableState.loading = it }
        ) {
            mutableState.error = null
            mutableState.items = repository.getBooks(loadFilters())
        }
    }

    private fun loadFavoriteBooks() {
        viewModelScope.launch {
            repository.getAllFavourites().collect {
                mutableState.favouriteBooks = it.map { book ->
                    Book(
                        id = book.id,
                        title = book.title,
                        description = book.description,
                        authors = book.authors,
                        publisher = book.publisher,
                        publishedDate = book.publishedDate,
                        language = book.language
                    )
                }
            }
        }
    }

    fun setSearchName(name: String) {
        mutableState.searchName = name
        viewModelScope.launch {
            DataPreferences.saveSearchName(context, name)
        }
    }

    fun addFavouriteBook(book: Book) {
        viewModelScope.launch {
            repository.addBookToFavourite(FavouriteBookEntity(
                id = book.id,
                description = book.description,
                authors = book.authors,
                language = book.language,
                publishedDate = book.publishedDate,
                publisher = book.publisher,
                title = book.title
            ))
        }
    }

    private class MutableListState : ListState {
        override var searchName: String by mutableStateOf("")
        override var items: List<Book> by mutableStateOf(emptyList())
        override var favouriteBooks: List<Book> by mutableStateOf(emptyList())
        override var error: String? by mutableStateOf(null)
        override var loading: Boolean by mutableStateOf(false)
    }
}