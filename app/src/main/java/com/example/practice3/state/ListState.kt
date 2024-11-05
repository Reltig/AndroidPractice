package com.example.practice3.state

import androidx.compose.runtime.Stable
import com.example.practice3.models.Book

@Stable
interface ListState {
    val searchName: String
    val items: List<Book>
    val favouriteBooks: List<Book>
    val error: String?
    var loading: Boolean
}