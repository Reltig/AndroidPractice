package com.example.practice3.data

import android.util.Log
import com.example.practice3.data.api.BooksApi
import com.example.practice3.data.mappers.BookResponseToEntityMapper
import com.example.practice3.models.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BooksRepository(
    private val api: BooksApi,
    private val mapper: BookResponseToEntityMapper
) : IBooksRepository {

    override suspend fun getBooks(query: String): List<Book> {
        return withContext(Dispatchers.IO) {
            val list = api.listBooks(query).execute().body()
            if (list != null) {
                return@withContext mapper.mapBooks(list)
            }
            return@withContext listOf()

        }
    }
    override suspend fun getBook(id: String): Book {
        return withContext(Dispatchers.IO) {
            mapper.mapBook(api.getBook(id))
        }
    }
}