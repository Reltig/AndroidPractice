package com.example.practice3

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.practice3.data.db.AppDatabase
import com.jakewharton.threetenabp.AndroidThreeTen
import com.example.practice3.di.apiModule
import com.example.practice3.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BookApp : Application(){
    val BookApp.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

        startKoin{
            androidLogger()
            androidContext(this@BookApp)
            modules(appModule, apiModule)
        }
    }
}