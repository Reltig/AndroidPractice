package com.example.practice3.data.api.response.Book

import androidx.annotation.Keep

@Keep
data class BookVolumeInfo(
    val title: String,
    val subtitle: String,
    val authors: List<String>,
    val publisher: String,
    val publishedDate: String,
    val description: String,
)
