package com.example.practice3.data

import com.example.practice3.models.Book

interface IBooksRepository {
    suspend fun getBooks(query: String) : List<Book>
    suspend fun getBook(id: String): Book
}