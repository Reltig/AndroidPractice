package com.example.practice3.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Query("SELECT * FROM favourite_book")
    fun getAll(): Flow<List<FavouriteBookEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(book: FavouriteBookEntity)

    @Delete
    suspend fun delete(book: FavouriteBookEntity)
}