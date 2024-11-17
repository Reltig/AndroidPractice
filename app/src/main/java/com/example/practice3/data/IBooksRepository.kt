package com.example.practice3.data

import com.example.practice3.data.db.FavouriteBookEntity
import com.example.practice3.models.Book
import kotlinx.coroutines.flow.Flow

interface IBooksRepository {
    suspend fun getBooks(query: String) : List<Book>
    suspend fun getBook(id: String): Book
    suspend fun addBookToFavourite(book: FavouriteBookEntity)
    suspend fun deleteBookFromFavourite(book: FavouriteBookEntity)
    suspend fun getAllFavourites() : Flow<List<FavouriteBookEntity>>
}