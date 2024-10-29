package com.example.practice3.data.mappers

import com.example.practice3.data.api.response.Book.BookResponse
import com.example.practice3.data.api.response.Book.BooksListResponse
import com.example.practice3.models.Book

class BookResponseToEntityMapper {
    fun mapBooks(response: BooksListResponse): List<Book> {
        return response.items.map {
            mapBook(it)
        }
    }
    fun mapBook(response: BookResponse): Book {
        return Book(
            id = response.id,
            title = response.volumeInfo.title,
            authors = response.volumeInfo.authors.joinToString(separator = ""),
            description = response.volumeInfo.description,
            publishedDate = response.volumeInfo.publishedDate,
            publisher = response.volumeInfo.publisher,
            language = response.accessInfo.language
        )
    }
}