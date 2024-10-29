package com.example.practice3.di

import com.example.practice3.BuildConfig
import com.example.practice3.data.BooksRepository
import com.example.practice3.data.IBooksRepository
import com.example.practice3.data.api.BooksApi
import com.example.practice3.data.api.Interceptors.TokenInterceptor
import com.example.practice3.data.mappers.BookResponseToEntityMapper
import com.example.practice3.viewModels.BookViewModel
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.viewModel
import org.koin.core.scope.get
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val appModule = module {
    single<IBooksRepository> { BooksRepository(get(), get()) }
    factory { BookResponseToEntityMapper() }
    viewModel<BookViewModel> { BookViewModel(get()) }
}
