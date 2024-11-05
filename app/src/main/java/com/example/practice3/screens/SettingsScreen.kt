package com.example.practice3.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.practice3.viewModels.BookViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen() {
    val viewModel = koinViewModel<BookViewModel>()
    val state = viewModel.viewState;

    Column {
        Text(text = "Settings", style = MaterialTheme.typography.titleLarge)
        TextField(
            value = state.searchName,
            onValueChange = { viewModel.setSearchName(it) },
            label = { Text("Book name") }
        )
        Button(
            onClick = {  }
        ) {
            Text("Apply settings")
        }
    }
}