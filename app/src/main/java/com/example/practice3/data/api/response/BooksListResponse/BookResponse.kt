package com.example.practice3.data.api.response.BooksListResponse

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class BookResponse(
    val id: String,
    @SerializedName("volumeInfo") val volumeInfo: BookVolumeInfo,
    @SerializedName("accessInfo") val accessInfo: AccessInfo
)
