package com.example.apps_news

import android.app.Application
import com.example.apps_news.di.appModule
import com.example.apps_news.di.useCaseModule
import com.example.apps_news.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NewsApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NewsApplication)
            modules(listOf(appModule, useCaseModule, viewModelModule))
        }
    }
}