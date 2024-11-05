package com.example.practice3.di

import android.content.Context
import androidx.datastore.dataStore
import androidx.room.Room
import com.example.practice3.BookApp
import com.example.practice3.BuildConfig
import com.example.practice3.data.api.BooksApi
import com.example.practice3.data.api.Interceptors.TokenInterceptor
import com.example.practice3.data.db.AppDatabase
import com.example.practice3.data.db.BookDao
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val apiModule = module {
    factory { provideRetrofit() }
    single { provideNetworkApi(get()) }
    single { provideFavouriteBookDb(androidContext()) }
}

fun provideRetrofit(): Retrofit {
    val intercepter = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }
    val client = OkHttpClient.Builder()
        .addInterceptor(TokenInterceptor())
        .addInterceptor(intercepter)
        .build()

    return Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

}

fun provideNetworkApi(retrofit: Retrofit): BooksApi =
    retrofit.create(BooksApi::class.java)

fun provideFavouriteBookDb(applicationContext: Context): BookDao {
    val db = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java,
        "database-app"
    ).build()
    return db.bookDao()
}