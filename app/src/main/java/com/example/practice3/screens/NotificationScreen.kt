package com.example.practice3.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.practice3.viewModels.BookViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NotificationScreen(controller: NavHostController) {
    val viewModel = koinViewModel<BookViewModel>()
    val state = viewModel.viewState

    Column(Modifier.fillMaxSize()) {

        val lazyColumnState = rememberSaveable(saver = LazyListState.Saver) {
            LazyListState(
                0,
                0
            )
        }

        state.error?.let {
            Text(text = it)
        }

        LazyColumn(
            Modifier.fillMaxSize(),
            lazyColumnState
        ) {
            items(state.favouriteBooks) { book ->
                ListItem(
                    modifier = Modifier.clickable { controller.navigate("element/${book.id}") },
                    headlineContent = {
                        Text(text = "Book: ${book.title} Authors: ${book.authors}")
                        Button(onClick = {viewModel.addFavouriteBook(book)}) {
                            Text("Make favourite")
                        }
                    }
                )
            }
        }
    }
}