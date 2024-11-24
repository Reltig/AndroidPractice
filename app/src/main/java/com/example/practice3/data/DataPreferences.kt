package com.example.practice3.data

import android.content.Context
import android.net.Uri
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "filter_preferences")

object DataPreferences {
    private val SEARCH_NAME = stringPreferencesKey("search_name")
    private val PROFILE_NAME = stringPreferencesKey("profile_name")
    private val URI_NAME = stringPreferencesKey("uri_name")
    private val URL_NAME = stringPreferencesKey("url_name")

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

    fun getProfileName(context: Context): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[PROFILE_NAME] ?: ""
        }
    }

    suspend fun saveProfileName(context: Context, nameFilter: String) {
        context.dataStore.edit { preferences ->
            preferences[PROFILE_NAME] = nameFilter
        }
    }

    fun getProfileUri(context: Context): Flow<Uri> {
        return context.dataStore.data.map { preferences ->
            Uri.parse(preferences[URI_NAME] ?: "")
        }
    }

    suspend fun saveProfileUri(context: Context, uri: Uri) {
        context.dataStore.edit { preferences ->
            preferences[URI_NAME] = uri.toString()
        }
    }

    fun getProfileUrl(context: Context): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[URL_NAME] ?: ""
        }
    }

    suspend fun saveProfileUrl(context: Context, nameFilter: String) {
        context.dataStore.edit { preferences ->
            preferences[URL_NAME] = nameFilter
        }
    }
}