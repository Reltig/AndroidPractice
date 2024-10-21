package com.example.practice3.viewModels

import androidx.lifecycle.ViewModel
import com.example.practice3.data.BooksRepository
import com.example.practice3.models.Book

class BookViewModel: ViewModel() {
    private val repository: BooksRepository = BooksRepository()

    val books: List<Book> = repository.getAll()

    fun getById(id: Int): Book? {
        return repository.get(id);
    }
}