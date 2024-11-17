package com.example.practice3.data

import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.example.practice3.data.api.BooksApi
import com.example.practice3.data.db.BookDao
import com.example.practice3.data.db.FavouriteBookEntity
import com.example.practice3.data.mappers.BookResponseToEntityMapper
import com.example.practice3.models.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class BooksRepository(
    private val api: BooksApi,
    private val mapper: BookResponseToEntityMapper,
    private val bookDao: BookDao
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

    override suspend fun addBookToFavourite(book: FavouriteBookEntity) {
//        bookDao.insert(FavouriteBookEntity(
//            id = book.id,
//            title = book.title,
//            description = book.description,
//            authors = book.authors,
//            publisher = book.publisher,
//            publishedDate = book.publishedDate,
//            language = book.language
//        ))
        bookDao.insert(book)
    }

    override suspend fun deleteBookFromFavourite(book: FavouriteBookEntity) = bookDao.delete(book)

    override suspend fun getAllFavourites() : Flow<List<FavouriteBookEntity>> = bookDao.getAll()
}