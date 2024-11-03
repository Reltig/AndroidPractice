package com.example.practice3.data.api.response.Book

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AccessInfo(
    @SerializedName("country") val language: String
)
