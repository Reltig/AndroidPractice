package com.example.practice3

import android.app.Application
import com.example.practice3.di.apiModule
import com.example.practice3.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BookApp : Application(){
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@BookApp)
            modules(appModule, apiModule)
        }
    }
}