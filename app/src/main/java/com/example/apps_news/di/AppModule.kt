package com.example.apps_news.di

import com.example.apps_news.data.api.NewsApiService
import com.example.apps_news.domain.repository.NewsRepository
import com.example.apps_news.domain.repository.NewsRepositoryImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.koin.dsl.module


val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApiService::class.java)
    }
    single<NewsRepository> { NewsRepositoryImpl(get()) }
}