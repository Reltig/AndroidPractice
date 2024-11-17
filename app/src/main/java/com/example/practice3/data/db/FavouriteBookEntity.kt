package com.example.practice3.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_book")
data class FavouriteBookEntity(
    @PrimaryKey val id: String,
    val title: String,
    val authors: String,
    val publisher: String,
    @ColumnInfo(name = "published_date") val publishedDate: String,
    val description: String,
    val language: String
)