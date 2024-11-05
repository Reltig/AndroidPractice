package com.example.practice3.data.api.response.BooksListResponse

import androidx.annotation.Keep

@Keep
data class BookVolumeInfo(
    val title: String,
    val authors: List<String>?,
    val publisher: String?,
    val publishedDate: String?,
    val description: String?,
)
