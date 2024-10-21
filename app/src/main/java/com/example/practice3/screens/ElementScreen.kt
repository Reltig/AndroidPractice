package com.example.practice3.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.practice3.viewModels.BookViewModel

@Composable
fun ElementScreen(controller: NavHostController, elementId: Int) {
    val viewModel = viewModel<BookViewModel>()
    val book = viewModel.getById(elementId);

    if(book == null){
        Text("Book not found")
        return
    }
    Column {
        Text(text = book.title, style = MaterialTheme.typography.titleLarge)
        Text(text = "Author(s): ${book.authors}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Publisher: ${book.publisher}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Published date: ${book.publishedDate}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Language: ${book.language}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Description: ${book.description}", style = MaterialTheme.typography.bodySmall)
    }
}