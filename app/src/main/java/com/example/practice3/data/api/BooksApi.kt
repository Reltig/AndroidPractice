package com.example.practice3.data.api

import com.example.practice3.data.api.response.BooksListResponse.BookResponse
import com.example.practice3.data.api.response.BooksListResponse.BooksListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path


interface BooksApi {
    @GET("volumes")
    fun listBooks(
        @Query("q") query: String?,
        @Query("startIndex") startIndex: Int = 0,
        @Query("maxResults") maxResults: Int = 3
    ): Call<BooksListResponse>

    @GET("volumes/{id}")
    fun getBook(
        @Path("id") query: String?,
    ): BookResponse
}