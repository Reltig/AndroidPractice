package com.example.practice3.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.practice3.viewModels.BookViewModel

@Composable
fun ListScreen(controller: NavHostController) {
    val viewModel = viewModel<BookViewModel>()
    val books = viewModel.books

    LazyColumn (Modifier.fillMaxSize()) {
        items(books) { book ->
            ListItem(
                modifier = Modifier.clickable { controller.navigate("element/${book.id}") },
                headlineContent = {
                    Text(text = "Film: ${book.title} Authors: ${book.authors}")
                }
            )
        }
    }
}