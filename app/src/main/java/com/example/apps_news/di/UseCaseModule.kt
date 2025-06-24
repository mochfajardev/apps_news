package com.example.apps_news.di

import com.example.apps_news.domain.usecase.GetArticlesUseCase
import com.example.apps_news.domain.usecase.GetCategoriesUseCase
import com.example.apps_news.domain.usecase.GetSourcesUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetCategoriesUseCase(get()) }
    single { GetSourcesUseCase(get()) }
    single { GetArticlesUseCase(get()) }
}