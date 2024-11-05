package com.example.practice3.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "filter_preferences")

object DataPreferences {
    private val SEARCH_NAME = stringPreferencesKey("search_name")

    fun getSearchName(context: Context): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[SEARCH_NAME] ?: ""
        }
    }

    suspend fun saveSearchName(context: Context, nameFilter: String) {
        context.dataStore.edit { preferences ->
            preferences[SEARCH_NAME] = nameFilter
        }
    }
}