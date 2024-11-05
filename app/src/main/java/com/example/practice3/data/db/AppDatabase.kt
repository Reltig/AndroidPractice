package com.example.practice3.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavouriteBookEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
}