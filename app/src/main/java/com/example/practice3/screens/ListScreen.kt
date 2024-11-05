package com.example.practice3.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ListItem
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.practice3.viewModels.BookViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListScreen(controller: NavHostController) {
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
            items(state.items) { book ->
                ListItem(
                    modifier = Modifier.clickable { controller.navigate("element/${book.id}") },
                    headlineContent = {
                        Text(text = "Book: ${book.title} Authors: ${book.authors}")
                    },
                    leadingContent = {
                        Button(
                            colors = ButtonColors(
                                contentColor = Color(0, 0, 0),
                                containerColor = Color(255, 255, 0),
                                disabledContentColor = Color(255, 255, 0),
                                disabledContainerColor = Color(255, 255, 0)
                            ),
                            onClick = {viewModel.addFavouriteBook(book)}) {
                            Text("Make favourite")
                        }
                    }
                )
            }
        }
    }

    if (state.loading) {
        Text("Loading")
    }
}