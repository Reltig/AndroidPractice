package com.example.practice3.data.api.response.Book

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class BooksListResponse(
    @SerializedName("totalItems") val totalItems: Int,
    val items: List<BookResponse>
)