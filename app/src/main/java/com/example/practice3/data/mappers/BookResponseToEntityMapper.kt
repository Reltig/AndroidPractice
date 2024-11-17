package com.example.practice3.data.mappers

import com.example.practice3.data.api.response.BooksListResponse.BookResponse
import com.example.practice3.data.api.response.BooksListResponse.BooksListResponse
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
            authors = response.volumeInfo.authors?.joinToString(separator = "") ?: "Not defined",
            description = response.volumeInfo.description ?: "Description not specified",
            publishedDate = response.volumeInfo.publishedDate ?: "Published date is unknown",
            publisher = response.volumeInfo.publisher ?: "Unknown publisher",
            language = response.accessInfo.language
        )
    }
}